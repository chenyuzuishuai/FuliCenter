package cn.ucai.fulicenter.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.L;

/**
 * Created by yu chen on 2017/1/17.
 */

public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBOpenHelper dbHelper;
    static DBManager dbMgr = new DBManager();
    public DBManager(){

    }
    public static void onInit(Context context){
        dbHelper = new DBOpenHelper(context);
    }
    public static DBManager getInstance(){
        if (dbHelper==null){
            L.e(TAG,"没有调用onInit");
        }
        return  dbMgr;
    }
    public boolean savUser (User user){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()){
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUME_NAME,user.getMuserName());
            values.put(UserDao.USER_COLUME_NICK,user.getMuserNick());
            values.put(UserDao.USER_COLUME_AVATAR,user.getMavatarId());
            values.put(UserDao.USER_COLUME_AVATAR_PATH,user.getMavatarPath());
            values.put(UserDao.USER_COLUME_AVATAR_TYPE,user.getMavatarType());
            values.put(UserDao.USER_COLUME_AVATAR_SUFFIX,user.getMavatarSuffix());
            values.put(UserDao.USER_COLUME_AVATAR_UPDATE_TIME,user.getMavatarLastUpdateTime());
            return db.replace(UserDao.USER_TABLE_NAME,null,values)!=-1;
        }
        return false;
    }
}
