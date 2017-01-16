package cn.ucai.fulicenter.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.CateFilterButton;


public class CategoryGoodsActivity extends AppCompatActivity {
    NewGoodsFragment mNewGoodsFragment;
    boolean priceAsc = false;
    boolean addTimeAsc = false;
    @BindView(R.id.btn_sort_price)
    Button btnSortPrice;
    @BindView(R.id.btn_sort_addtime)
    Button btnSortAddtime;
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.buttonCateFilter)
    CateFilterButton buttonCateFilter;
    @BindView(R.id.layout_sort)
    LinearLayout layoutSort;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.activity_category_goods_details)
    LinearLayout activityCategoryGoodsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_goods);
        ButterKnife.bind(this);
        mNewGoodsFragment = new NewGoodsFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mNewGoodsFragment)
                .commit();
        String groupName = getIntent().getStringExtra(I.CategoryGroup.NAME);
        buttonCateFilter.initCateFilter(groupName, null);
    }


    @OnClick({R.id.btn_sort_price, R.id.btn_sort_addtime})
    public void onClick(View view) {
        Drawable right;
        switch (view.getId()) {
            case R.id.btn_sort_price:
                if (priceAsc) {
                    mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_ASC);
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_DESC);
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                priceAsc = !priceAsc;
                break;
            case R.id.btn_sort_addtime:
                if (addTimeAsc) {
                    mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_ASC);
                    right = getResources().getDrawable(R.mipmap.arrow_order_up);
                } else {
                    mNewGoodsFragment.sortGoods(I.SORT_BY_ADDTIME_DESC);
                    right = getResources().getDrawable(R.mipmap.arrow_order_down);
                }
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
                btnSortAddtime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
                addTimeAsc = !addTimeAsc;
                break;
        }
    }

    @OnClick(R.id.ivback)
    public void onClick() {
        MFGT.finish(this);
    }
}
