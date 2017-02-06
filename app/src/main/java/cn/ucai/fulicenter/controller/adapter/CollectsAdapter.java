package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

import static cn.ucai.fulicenter.application.I.TYPE_FOOTER;
import static cn.ucai.fulicenter.application.I.TYPE_ITEM;

/**
 * Created by yu chen on 2017/2/6.
 */

public class CollectsAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<CollectBean> mList;
    IModelUser model;
    User user;




    public CollectsAdapter(Context mContext, ArrayList<CollectBean> list) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mList.addAll(list);
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
    }

    public void initData(ArrayList<CollectBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();

    }

    public void removeItem(int goodsId) {
        if (goodsId != 0) {
            mList.remove(new CollectBean(goodsId));
            notifyDataSetChanged();
        }
    }



    boolean more;

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    String footer;

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout;
        switch (viewType) {
            case TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_footer, null);
                return new FooterViewHolder(layout);
            case TYPE_ITEM:
                layout = inflater.inflate(R.layout.item_collect, null);
                return new CollectViewHolder(layout);
        }
        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterViewHolder holder1 = (FooterViewHolder) holder;
            holder1.tvFooter.setText(getFooter());
            return;
        }
        CollectViewHolder holder1 = (CollectViewHolder) holder;
        if (holder != null) {
            holder1 = (CollectViewHolder) holder;
        }

        holder1.bind(position);


    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThume)
        ImageView ivGoodsThume;
        @BindView(R.id.iv_collect_del)
        ImageView ivCollectDel;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;

        int itemPosition;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final int position) {
            ImageLoader.downloadImg(mContext, ivGoodsThume, mList.get(position).getGoodsThumb());
            tvGoodsName.setText(mList.get(position).getGoodsName());
            itemPosition = position;

        }

        @OnClick(R.id.layout_goods)
        public void details() {
            MFGT.gotoGoodsDetail(mContext, mList.get(itemPosition).getGoodsId());

        }

        @OnClick(R.id.iv_collect_del)
        public void delCollect() {
            model.setCollect(mContext, mList.get(itemPosition).getGoodsId(), user.getMuserName(), I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        mList.remove(itemPosition);
                        notifyDataSetChanged();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });

        }


    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
