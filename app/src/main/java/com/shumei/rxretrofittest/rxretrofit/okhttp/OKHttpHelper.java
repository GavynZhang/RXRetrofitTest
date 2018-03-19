package com.shumei.rxretrofittest.rxretrofit.okhttp;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static com.shumei.rxretrofittest.rxretrofit.Config.DEFAULT_TIMEOUT;

/**
 * Created by GavynZhang on 2017/12/4 21:57.
 */

public class OKHttpHelper {

    private static final int CACHE_SIZE = 20 * 1024 * 1024;

    public static Cache provideOkHttpCache(Context context){
        Cache cache = new Cache(context.getCacheDir(), CACHE_SIZE);
        return cache;
    }

    public static OkHttpClient provideOkHttpClient(Cache cache, CacheInterceptor cacheInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//失败时重新连接
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(cacheInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .build();

        return client;
    }
}
