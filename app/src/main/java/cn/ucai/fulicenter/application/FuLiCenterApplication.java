package cn.ucai.fulicenter.application;

import android.app.Application;

import java.util.HashMap;

import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by yu chen on 2017/1/10
 */

public class FuLiCenterApplication extends Application {
    private static FuLiCenterApplication instance;
    public static FuLiCenterApplication getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCenterApplication.user = user;
    }

    private static User user;
    private static HashMap<Integer,CartBean> myCartList = new HashMap<>();
   private static HashMap<Integer,CartBean> getmyCartList(){
       return getmyCartList();
   }
    private static void setMyCartList(HashMap<Integer,CartBean> myCartList){
        FuLiCenterApplication.myCartList = myCartList;
    }

}
