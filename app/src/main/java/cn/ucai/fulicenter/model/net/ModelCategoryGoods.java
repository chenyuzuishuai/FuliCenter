package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/13.
 */

public class ModelCategoryGoods implements IModelCategoryGoods {
    @Override
    public void downData(Context context, int catId, int pageId,  OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener) {
        OkHttpUtils<NewGoodsBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_GOODS_DETAILS)
                .addParam(I.CategoryChild.CAT_ID,String.valueOf(catId))
                .addParam(I.Goods.KEY_GOODS_ID,String.valueOf(pageId))
                .addParam(I.PAGE_SIZE,String.valueOf(10))
                .targetClass(NewGoodsBean[].class)
                .execute(listener);
    }
}
