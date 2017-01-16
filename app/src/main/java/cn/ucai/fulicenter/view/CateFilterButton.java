package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.activity.CategoryGoodsActivity;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by yu chen on 2017/1/16.
 */

public class CateFilterButton extends Button {
    boolean isExpan;
    Context mContext;
    PopupWindow mPopupWindow;
    CateFliterApdater adapter;
    GridView mGridView;
    String groupName;
    public CateFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public void initCateFilter(String groupName, ArrayList<CategoryChildBean> list) {
        this.groupName = groupName;
        this.setText(groupName);
        setCateFilterButtonListener();
        adapter = new CateFliterApdater(mContext,list);
        initGridView();
    }

    private void initGridView() {
        mGridView = new GridView(mContext);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setNumColumns(mGridView.AUTO_FIT);
        mGridView.setAdapter(adapter);
    }

    private void setCateFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpan) {
                    initPopupWindow();
                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPopupWindow() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));

        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }

    private void setArrow() {
        Drawable right;
        if (isExpan) {
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        } else {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
        isExpan = !isExpan;
    }

    class CateFliterApdater extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> list;

        public CateFliterApdater(Context context, ArrayList<CategoryChildBean> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CategoryChildBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            CateFliterViewHolder vh = null;
            if (view == null) {
                view = View.inflate(context, R.layout.item_cate_fliter, null);
                vh = new CateFliterViewHolder(view);
                view.setTag(vh);
            }else {
                vh = (CateFliterViewHolder) view.getTag();
            }
            vh.bind(position);
            return view;
        }

         class CateFliterViewHolder {
            @BindView(R.id.ivCategoryChildThume)
            ImageView ivCategoryChildThume;
            @BindView(R.id.tvCategoryChildName)
            TextView tvCategoryChildName;
            @BindView(R.id.layout_category_child)
            RelativeLayout layoutCategoryChild;

            CateFliterViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

             public void bind(final int position) {
                 ImageLoader.downloadImg(context,ivCategoryChildThume,
                         list.get(position).getImageUrl()
                 );
                 tvCategoryChildName.setText(list.get(position).getName());
                 layoutCategoryChild.setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         MFGT.gotoCategoryGoods(mContext,
                             list.get(position).getId(),
                                 groupName,
                                 list
                                 );
                         MFGT.finish((CategoryGoodsActivity)mContext);
                     }
                 });
             }
         }
    }
}
