package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;

public class SettingsActivity extends AppCompatActivity {

    OnSetAvatarListener mOnsetAvatarListener;
    @BindView(R.id.ivUserAvatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserNick)
    TextView tvUserNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        // DisplayUtils.initBackWithTitle(this,"设置");
        initData();
    }

    private void initData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUser(user);
        } else {
            MFGT.gotoLogin(this);
        }
    }

    private void loadUser(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, ivUserAvatar);
        tvUserName.setText(user.getMuserName());
        tvUserNick.setText(user.getMuserNick());
    }


    @OnClick(R.id.btBack)
    public void onClick() {
        FuLiCenterApplication.setUser(null);
        SharePrefrenceUtils.getInstance(this).removeUser();
        MFGT.gotoLogin(this);
        finish();
    }

    @OnClick(R.id.layout_user_nickname)
    public void updateNick() {
        MFGT.gotoUpDateNick(this);

    }

    @OnClick(R.id.iv_layout_avatar)
    public void onClickAvatar() {
        mOnsetAvatarListener = new OnSetAvatarListener(this,
                R.id.layout_user_avatar,
                FuLiCenterApplication.getUser().getMuserName(),
                I.AVATAR_TYPE_USER_PATH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == I.REQUEST_CODE_NICK) {
            tvUserNick.setText(FuLiCenterApplication.getUser().getMuserNick());
        }
    }


}
