package com.android.byc.myhousecoins.myapplication;

import android.app.Application;
import android.content.Context;

import com.android.byc.myhousecoins.db.DaoMaster;
import com.android.byc.myhousecoins.db.DaoSession;
import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/22 9:23
 * @description
 */
public class MyApplication extends Application {
    private static DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this,"MY_COINS");
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }


    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
