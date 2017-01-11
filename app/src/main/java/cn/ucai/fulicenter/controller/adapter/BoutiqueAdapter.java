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
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;


/**
 * Created by yu chen on 2017/1/11.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    final int TYPE_BOUTIQUE = 0;
    final int TYPE_FOOTER = 1;
    Context mContext;
    ArrayList<BoutiqueBean> mList;

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


    public BoutiqueAdapter(Context context, ArrayList<BoutiqueBean> list) {
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
            case TYPE_BOUTIQUE:
                layout = inflater.inflate(R.layout.boutique_adapter, null);
                return new BoutiqueViewHolder(layout);
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
        BoutiqueViewHolder vh = (BoutiqueViewHolder) holder;
        if (holder != null) {
            vh = (BoutiqueViewHolder) holder;
        }
        ImageLoader.downloadImg(mContext, vh.ivBoutique, mList.get(position).getImageurl());
        vh.tvBoutique1.setText(mList.get(position).getTitle());
        vh.tvBoutique2.setText(mList.get(position).getDescription());
        vh.tvBoutique3.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    public void initData(ArrayList<BoutiqueBean> list) {
        if (mList != null) {
            this.mList.clear();
        }
        addData(list);
    }

    public void addData(ArrayList<BoutiqueBean> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return TYPE_BOUTIQUE;
    }


    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.tvBoutique1)
        TextView tvBoutique1;
        @BindView(R.id.tvBoutique2)
        TextView tvBoutique2;
        @BindView(R.id.tvBoutique3)
        TextView tvBoutique3;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
