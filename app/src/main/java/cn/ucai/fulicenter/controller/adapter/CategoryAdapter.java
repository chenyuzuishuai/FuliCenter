package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by yu chen on 2017/1/13.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    ArrayList<CategoryGroupBean> mGroupBean;
    ArrayList<ArrayList<CategoryChildBean>> mChildBean;
    public  CategoryAdapter(Context context,ArrayList<CategoryGroupBean> groupBean,
                            ArrayList<CategoryChildBean> childBean ){
        mContext = context;
        mGroupBean = new ArrayList<>();
        mGroupBean.addAll(groupBean);
    }

    @Override
    public int getGroupCount() {
        return mGroupBean!=null?0:mGroupBean.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildBean==null||mChildBean.get(groupPosition)==null?0:mChildBean.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupBean.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildBean.get(groupPosition).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
     View inflate =   LayoutInflater.from(mContext).inflate(R.layout.item_category_group,null);
        return inflate;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
