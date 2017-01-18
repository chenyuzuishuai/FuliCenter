package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/12.
 */

public interface IModelGoodsDetails {
    void  downData(Context context, int goodsId ,OkHttpUtils.OnCompleteListener<GoodsDetailsBean> listener);
    void isCollect(Context context, int goodsId, String username, OnCompleteListener<MessageBean> listener);
    void setCollect(Context context, int goodsId, String username, int action, OkHttpUtils.OnCompleteListener<MessageBean> listener);
}
