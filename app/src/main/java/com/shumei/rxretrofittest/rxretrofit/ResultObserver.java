package com.shumei.rxretrofittest.rxretrofit;

import com.shumei.rxretrofittest.rxretrofit.bean.HttpResult;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by GavynZhang on 2017/12/6 15:57.
 * 暂时不能使用
 */

public abstract class ResultObserver<T extends HttpResult<E>, E> implements Observer {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException){
            //网络超时
        }else if (e instanceof ConnectException){
            //无网络
        }
    }

    @Override
    public void onComplete() {

    }
}
