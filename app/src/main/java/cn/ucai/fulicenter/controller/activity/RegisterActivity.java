package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;

  IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivReturn, R.id.iv_avatar, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivReturn:
                MFGT.finish(this);
                break;
            case R.id.btnRegister:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String userNick = etNick.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(userNick)) {
            etNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            etNick.requestFocus();
        }else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        }else if (TextUtils.isEmpty(confirm)) {
            etConfirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            etConfirmPassword.requestFocus();
        }else if (TextUtils.isEmpty(userNick)) {
            etConfirmPassword.setError(getResources().getString(R.string.two_input_password));
            etConfirmPassword.requestFocus();
        }else {
            register(username,userNick,password);
        }

    }

    private void register(String username, String userNick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model = new ModelUser();
        model.register(this, username, userNick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, Result.class);
                    Log.e(">>>>>", "result=" + result);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            CommonUtils.showLongToast(R.string.register_success);
                        }else {
                            CommonUtils.showLongToast(R.string.register_fail_exists);

                        }
                    }else {
                        CommonUtils.showLongToast(R.string.register_fail);
                    }

                }
                dialog.dismiss();

            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
