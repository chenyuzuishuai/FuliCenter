package cn.ucai.fulicenter.view;

/**
 * Created by yu chen on 2017/1/10.
 */

import android.app.Activity;
import android.content.Intent;

import cn.ucai.fulicenter.R;

/**
 * 定义切换Activity时的动画
 */
public class MFGT {
    public static void  startActivity(Activity context,Class<?> cls){
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        context.startActivity(new Intent(context,cls));
    }
}
