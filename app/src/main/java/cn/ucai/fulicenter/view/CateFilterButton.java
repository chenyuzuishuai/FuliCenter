package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by yu chen on 2017/1/16.
 */

public class CateFilterButton extends Button {
    boolean isExpan;
    Context mContext;
    PopupWindow mPopupWindow;

    public CateFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void initCateFilter(String groupName, ArrayList<CategoryGroupBean> list) {
        this.setText(groupName);
        setCateFilterButtonListener();
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
        TextView textView = new TextView(mContext);
        textView.setText("GOGOGO");
        mPopupWindow.setContentView(textView);
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
        }
    }
}
