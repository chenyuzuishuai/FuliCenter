package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.utils.MFGT;

public class LoginActivity extends AppCompatActivity {
IModelUser model;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.rl_username)
    RelativeLayout rlUsername;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.rl_password)
    RelativeLayout rlPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnUrl)
    Button btnUrl;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivback, R.id.btnLogin, R.id.btnRegister, R.id.btnUrl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivback:
                MFGT.finish(this);
                break;
            case R.id.btnLogin:
                checkInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegister(this);
                break;

        }
    }

    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            etUserName.setError(getString(R.string.user_name_connot_be_empty));
        }
    }
}
