package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CartFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.controller.fragment.PersonalCenterFragment;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;

public class MainActivity extends AppCompatActivity {

    RadioButton rbNewGoods, rbBoutique, rbCategory, rbCart, rbPersonalCenter;
    RadioButton[] rbs = new RadioButton[5];
    int index, currentIndex;
    NewGoodsFragment mNewGoodsFragment;
    BoutiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    CartFragment mCartFragment;
    PersonalCenterFragment mPersonalCenterFragment;
    Fragment[] mFragment = new Fragment[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbNewGoods = (RadioButton) findViewById(R.id.layout_new_goods);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonalCenter = (RadioButton) findViewById(R.id.layout_personal_center);

        rbs[0] = rbNewGoods;
        rbs[1] = rbBoutique;
        rbs[2] = rbCategory;
        rbs[3] = rbCart;
        rbs[4] = rbPersonalCenter;
        mCategoryFragment = new CategoryFragment();
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BoutiqueFragment();
        mCartFragment = new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
        mFragment[0] = mNewGoodsFragment;
        mFragment[1] = mBoutiqueFragment;
        mFragment[2] = mCategoryFragment;
        mFragment[3] = mCartFragment;
        mFragment[4] = mPersonalCenterFragment;
        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, mNewGoodsFragment)
                .add(R.id.fragment_container, mBoutiqueFragment)
                .add(R.id.fragment_container, mCategoryFragment)
                .show(mNewGoodsFragment)
                .hide(mBoutiqueFragment)
                .hide(mCategoryFragment)
                .hide(mPersonalCenterFragment)
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_goods:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
                if (FuLiCenterApplication.getUser()==null){
                    MFGT.gotoLogin(this,I.REQUEST_CODE_LOGIN_FROM_CART);
                }else {
                    index = 3;
                }
                break;
            case R.id.layout_personal_center:
                if (FuLiCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
        if (index != currentIndex) {
            setRadioStatus();
        } else {
            getSupportFragmentManager().beginTransaction().show(mFragment[index]).commit();
        }

    }

    private void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(mFragment[currentIndex]);
        if (!mFragment[index].isAdded()) {
            ft.add(R.id.fragment_container, mFragment[index]);
        }
        ft.show(mFragment[index]).commit();
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e("TAG", "onResume,currentIndex=" + currentIndex + ",index" + index
                + ",user=" + FuLiCenterApplication.getUser());
        if (index == 4 && FuLiCenterApplication.getUser() == null) {
            index = 0;
        }
        setRadioStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        L.e("TAG", "onActivityResult,resultCode" + requestCode + ",requestCode=" + requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK ) {
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }

        }
            setFragment();
            setRadioStatus();
    }
}