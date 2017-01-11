package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by yu chen on 2017/1/11.
 */

public interface IModelBoutique {
    void downloadBoutique(Context context, OkHttpUtils.OnCompleteListener<BoutiqueBean[]> listener);
}
