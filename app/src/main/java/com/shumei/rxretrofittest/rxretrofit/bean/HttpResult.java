package com.shumei.rxretrofittest.rxretrofit.bean;

import java.io.Serializable;

/**
 * Created by GavynZhang on 2017/12/2 17:16.
 * 用于封装网络请求返回的数据
 */

public class HttpResult<T> implements Serializable{
    //请求码
    public String code;
    public String msg;
    //数据
    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return "HttpResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
