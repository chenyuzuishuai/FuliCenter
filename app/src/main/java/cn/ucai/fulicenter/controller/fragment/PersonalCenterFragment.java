package cn.ucai.fulicenter.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {


    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.layout_center_collect)
    LinearLayout layoutCenterCollect;
    @BindView(R.id.center_user_collects)
    RelativeLayout centerUserCollects;
    @BindView(R.id.center_user_order_lis)
    GridView centerUserOrderLis;
    @BindView(R.id.ll_user_life)
    LinearLayout llUserLife;
    @BindView(R.id.ll_user_store)
    LinearLayout llUserStore;
    @BindView(R.id.ll_user_members)
    LinearLayout llUserMembers;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        initDate();
        return layout;
    }

    private void initDate() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            //MFGT.finish(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initDate();
    }

    private void loadUserInfo(User user) {
        //ImageLoader.downloadImg(getContext(),ivUserAvatar,user.getAvatarPath());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), ivUserAvatar);
        tvUserName.setText(user.getMuserNick());
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void settings() {
        MFGT.gotoSettings(getActivity());
    }


}
