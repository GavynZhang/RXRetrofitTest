package com.shumei.rxretrofittest.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shumei.rxretrofittest.rxretrofit.ActivityLifecycleEvent;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by GavynZhang on 2017/11/30 20:15.
 */

public class BaseActivity extends AppCompatActivity {

    //发射生命周期事件
    public final PublishSubject<ActivityLifecycleEvent> mLifecycleEventSubject = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.CREATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.START);
        super.onStart();
    }

    @Override
    protected void onResume() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.RESUME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        mLifecycleEventSubject.onNext(ActivityLifecycleEvent.RESTART);
        super.onRestart();
    }


    public <T> ObservableTransformer<T, T> bindUntilEvent(final ActivityLifecycleEvent untilEvent){
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                //takeUntil()操作符：发射来自原始Observable的数据，直到作为参数的Observable发送了数据
                return upstream.takeUntil(
                        //skipWhile一直丢弃发出的事件，直到返回false，然后发送剩下的事件，
                        // 这里就是直到生命周期到设置的生命周期的时候event!=untilEvent为false,就发出事件，
                        // 此时upstream为takeUntil，也就是检测到括号中的Observable发出了事件，则停止发送，
                        // 保证了在到了设置的生命周期的时候，不内存泄漏
                        mLifecycleEventSubject.skipWhile(new Predicate<ActivityLifecycleEvent>() {
                    @Override
                    public boolean test(ActivityLifecycleEvent event) throws Exception {
                        return event != untilEvent;
                    }
                }));
            }
        };
    }
}
