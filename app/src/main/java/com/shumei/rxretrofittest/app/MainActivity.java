package com.shumei.rxretrofittest.app;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shumei.rxretrofittest.R;
import com.shumei.rxretrofittest.bean.Latest;
import com.shumei.rxretrofittest.rxretrofit.ActivityLifecycleEvent;
import com.shumei.rxretrofittest.rxretrofit.RXSchedulers;
import com.shumei.rxretrofittest.rxretrofit.Utils.ApiFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends BaseActivity {

    private static final String BASE_URL = "https://news-at.zhihu.com/api/4/news/";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.text_view);

//        doRXRetrofitSimpleRequest();
        doMyRetrofitRequest();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //doTest();

            }
        }).start();
    }

    void doRXRetrofitSimpleRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build())
                .build();
        APIService service = retrofit.create(APIService.class);
        service.getLatestNews().subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Latest>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Latest latest) {
                        Log.d("MainActivity", latest.toString());
                        mTextView.setText(latest.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", " error: " + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    void doMyRetrofitRequest() {
        APIService service = ApiFactory.getAPIService(APIService.class, BASE_URL, MainActivity.this);
        service.getLatestNews()
                .compose(RXSchedulers.applyIOMain())
                .compose(bindUntilEvent(ActivityLifecycleEvent.DESTROY))
                .subscribe(new Observer<Latest>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(Latest latest) {
                                   Log.d("MainActivity", latest.toString());
                                   mTextView.setText(latest.toString());
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e("MainActivity", " error: " + e.toString());
                               }

                               @Override
                               public void onComplete() {

                               }
                           }

                );
    }

    void doTest() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 10; i++) {
                    e.onNext(i);
                    Thread.sleep(500);
                }
            }
        });

        Observable<Integer> integerObservable = Observable.create(e -> {
            Thread.sleep(5000);
            e.onNext(3);
        });
        observable
                .takeUntil(integerObservable)
                //.takeWhile(i -> i < 6)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.e("doTest", "Integer: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
