package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by yu chen on 2017/1/13.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBean;
    ArrayList<ArrayList<CategoryChildBean>> mChildBean;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> groupBean,
                           ArrayList<ArrayList<CategoryChildBean>> childBean) {
        mContext = context;
        mGroupBean = new ArrayList<>();
        mGroupBean.addAll(groupBean);
        mChildBean = new ArrayList<>();
        mChildBean.addAll(childBean);
    }

    @Override
    public int getGroupCount() {
        return mGroupBean != null ? mGroupBean.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBean == null || mChildBean.get(groupPosition) == null ? 0 : mChildBean.get(groupPosition).size();
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        if (mGroupBean != null && mGroupBean.get(groupPosition) != null) {
            return mGroupBean.get(groupPosition);
        }
        return null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mGroupBean != null && mChildBean.get(groupPosition).get(childPosition) != null) {
            return mChildBean.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder vh;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            vh = new GroupViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (GroupViewHolder) view.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivGroupThumb, mGroupBean.get(groupPosition).getImageUrl());
        vh.tvGroupName.setText(mGroupBean.get(groupPosition).getName());
        vh.ivIndicator.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            vh = new ChildViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ChildViewHolder) convertView.getTag();
        }
        ImageLoader.downloadImg(mContext, vh.ivCategoryChildThume, getChild(groupPosition, childPosition).getImageUrl());
        vh.tvCategoryChildName.setText(getChild(groupPosition, childPosition).getName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoCategoryGoods(mContext,
                        getChild(groupPosition,childPosition).getId()
                ,mGroupBean.get(groupPosition).getName(),
                 mChildBean.get(groupPosition)      );
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void initData(ArrayList<CategoryGroupBean> groupBean, ArrayList<ArrayList<CategoryChildBean>> childBean) {
        mGroupBean.clear();
        mGroupBean.addAll(groupBean);
        mChildBean.clear();
        mChildBean.addAll(childBean);
        notifyDataSetChanged();
    }


    static class GroupViewHolder {
        @BindView(R.id.ivGroupThumb)
        ImageView ivGroupThumb;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.ivIndicator)
        ImageView ivIndicator;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.ivCategoryChildThume)
        ImageView ivCategoryChildThume;
        @BindView(R.id.tvCategoryChildName)
        TextView tvCategoryChildName;
        @BindView(R.id.layout_category_child)
        RelativeLayout layoutCategoryChild;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
