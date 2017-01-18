package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);
    void register(Context context, String username, String userNick ,String password, OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String usernick, OkHttpUtils.OnCompleteListener<String> listener );
    void uploadAvatar(Context context, String username, File file,OnCompleteListener<String> listener);
}
