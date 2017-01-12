package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;


/**
 * Created by yu chen on 2017/1/11.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    final int TYPE_BOUTIQUE = 0;
    final int TYPE_FOOTER = 1;
    Context mContext;
    ArrayList<BoutiqueBean> mList;


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

            case TYPE_BOUTIQUE:
                layout = inflater.inflate(R.layout.boutique_adapter, null);
                return new BoutiqueViewHolder(layout);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        BoutiqueViewHolder vh = (BoutiqueViewHolder) holder;
        if (holder != null) {
            vh = (BoutiqueViewHolder) holder;
        }
        ImageLoader.downloadImg(mContext, vh.ivBoutique, mList.get(position).getImageurl());
        vh.tvBoutique1.setText(mList.get(position).getTitle());
        vh.tvBoutique2.setText(mList.get(position).getDescription());
        vh.tvBoutique3.setText(mList.get(position).getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mContext.startActivity(new Intent(mContext, BoutiqueChildActivity.class)
               .putExtra(I.NewAndBoutiqueGoods.CAT_ID,mList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
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

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivBoutique)
        ImageView ivBoutique;
        @BindView(R.id.tvBoutique1)
        TextView tvBoutique1;
        @BindView(R.id.tvBoutique2)
        TextView tvBoutique2;
        @BindView(R.id.tvBoutique3)
        TextView tvBoutique3;
        @BindView(R.id.LayoutBoutiqueItem)
        LinearLayout LayoutBoutiqueItem;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }
}
