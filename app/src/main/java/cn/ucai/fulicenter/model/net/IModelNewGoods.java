package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/11.
 */

public interface IModelNewGoods {
  void downNewGoods(Context context, int catId, int pageId, OkHttpUtils.OnCompleteListener<NewGoodsBean[]> listener);
}
