package com.android.byc.myhousecoins.utility;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 11:39
 * @description
 */
public class Response <T>{

    public int ret;
    public String msg;
    public T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
