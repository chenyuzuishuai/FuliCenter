package cn.ucai.fulicenter.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulicenter.R;

/**
 * �����л�Activityʱ�Ķ���
 */
public class MFGT {
    public static void  startActivity(Activity context, Class<?> cls){
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        context.startActivity(new Intent(context,cls));
    }
    public static void gotoBoutiqueChildActivity(Context context,int catId){
        Intent intent = new Intent();

    }
}
