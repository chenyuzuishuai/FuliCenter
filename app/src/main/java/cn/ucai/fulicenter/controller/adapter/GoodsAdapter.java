package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;


/**
 * Created by yu chen on 2017/1/11.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    final int TYPE_NERGOODS = 0;
    final int TYPE_FOOTER = 1;
    Context mContext;
    ArrayList<NewGoodsBean> mList;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    boolean isMore;
    boolean isDragging;
    String footer;


    public GoodsAdapter(Context context, ArrayList<NewGoodsBean> list) {
        mContext = context;
        mList = list;
        this.mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = null;
        switch (viewType) {
            case TYPE_FOOTER:
                layout = inflater.inflate(R.layout.item_footer, null);
                return new FooterViewHolder(layout);
            case TYPE_NERGOODS:
                layout = inflater.inflate(R.layout.new_goods_adapter, null);
                return new GoodsViewHolder(layout);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_FOOTER) {
            FooterViewHolder fv = (FooterViewHolder) holder;
            fv.tvFooter.setText(getFooter());
            return;
        }
        GoodsViewHolder vh = (GoodsViewHolder) holder;
        if (holder != null) {
            vh = (GoodsViewHolder) holder;
        }
        ImageLoader.downloadImg(mContext, vh.ivGoodsThume, mList.get(position).getGoodsThumb());
        vh.tvGoodsName.setText(mList.get(position).getGoodsName());
        vh.tvGoodsPrice.setText(mList.get(position).getCurrencyPrice());
    }


    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        this.mList.clear();
        addData(list);
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }


    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class GoodsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThume)
        ImageView ivGoodsThume;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return TYPE_NERGOODS;
    }
}
