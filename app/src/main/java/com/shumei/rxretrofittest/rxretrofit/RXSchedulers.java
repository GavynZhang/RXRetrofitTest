package com.shumei.rxretrofittest.rxretrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by GavynZhang on 2017/12/2 17:08.
 */

public class RXSchedulers {

    //命名：第一个为subscribeOn的线程，第二个为observerOn的线程

    public static <T> ObservableTransformer<T, T> applyIOMain(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
