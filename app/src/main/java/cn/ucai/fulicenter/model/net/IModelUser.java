package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/16.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);
    void register(Context context, String username, String userNick ,String password, OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String usernick, OkHttpUtils.OnCompleteListener<String> listener );
    void uploadAvatar(Context context, String username, File file,OnCompleteListener<String> listener);
    void collectCount(Context context,String username, OnCompleteListener<MessageBean> listener);
    void getCollects(Context context, String username , int pageId, int pageSize, OnCompleteListener<CollectBean[]> listener);

    void setCollect(Context mContext, int goodsId, String muserName, int actionDeleteCollect, OnCompleteListener<MessageBean> listener);
}
