package com.android.byc.myhousecoins.db;

import com.android.byc.myhousecoins.interfaces.EntityIgnore;

import java.io.Serializable;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 14:59
 * @description
 */
public class HttpResponse implements Serializable {
    @EntityIgnore
    private int ret;

    @EntityIgnore
    public String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
