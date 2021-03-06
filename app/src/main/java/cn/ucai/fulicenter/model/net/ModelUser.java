package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/16.
 */

public class ModelUser implements IModelUser {
    @Override
    public void login(Context context, String username, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void register(Context context, String username, String userNick, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK,userNick)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .post()
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void updateNick(Context context, String username, String usernick, OkHttpUtils.OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.User.NICK, usernick)
                .targetClass(String.class)
                .execute(listener);

    }

    @Override
    public void uploadAvatar(Context context, String username, File file, OnCompleteListener<String> listener) {
       OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.AVATAR_TYPE,I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void collectCount(Context context, String username,OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME,username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void getCollects(Context context, String username, int pageId, int pageSize, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME,username)
                .addParam(I.PAGE_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(pageSize))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    @Override
    public void setCollect(Context mContext, int goodsId, String muserName, int actionDeleteCollect, OnCompleteListener<MessageBean> listener) {
 OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(mContext);
        String url = I.REQUEST_ADD_COLLECT;
     if (actionDeleteCollect == I.ACTION_DELETE_COLLECT){
         url = I.REQUEST_DELETE_COLLECT;
     }
        utils.setRequestUrl(url)
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Collect.USER_NAME,muserName)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

   @Override
    public void getCart(Context context, String username, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.Cart.USER_NAME,username)
                .targetClass(CartBean[].class)
                .execute(listener);
    }


   private void addCart(Context context, String username, int goodsId, int count, OnCompleteListener<MessageBean> listener) {
OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.User.USER_NAME,username)
                .addParam(I.Cart.GOODS_ID,String.valueOf(goodsId))
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);
    }


   private void delCart(Context context, int cartId, OnCompleteListener<MessageBean> listener) {
   OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }


    private void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener) {
   OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID,String.valueOf(cartId))
                .addParam(I.Cart.COUNT,String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED,String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    @Override
    public void updateCart(Context context, int action, String username, int goodsId,int cartId, int count, OnCompleteListener<MessageBean> listener) {

        if (action == I.ACTION_CART_DEL) {
            delCart(context, 0, listener);
        } else if (action == I.ACTION_CART_UPDATE) {
            updateCart(context, 0, count, listener);
        } else {
            addCart(context, username, goodsId, 1, listener);
        }
    }

}
