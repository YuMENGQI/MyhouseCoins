package com.android.byc.myhousecoins.presenter;

import android.content.Context;

import com.android.byc.myhousecoins.db.DBManager;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 15:47
 * @description
 */
public class BasePresenter<T> {
    public T view;
    public Context context;
    public DBManager dbManager;

}
