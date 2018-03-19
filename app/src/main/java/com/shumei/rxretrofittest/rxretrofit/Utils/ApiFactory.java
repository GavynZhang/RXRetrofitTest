package com.shumei.rxretrofittest.rxretrofit.Utils;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shumei.rxretrofittest.rxretrofit.okhttp.CacheInterceptor;
import com.shumei.rxretrofittest.rxretrofit.okhttp.OKHttpHelper;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by GavynZhang on 2017/11/29 18:12.
 */

public class ApiFactory {

    public static <T> T getAPIService(Class<T> clazz, String baseUrl, Context context) {

        Cache cache = OKHttpHelper.provideOkHttpCache(context);
        OkHttpClient client = OKHttpHelper.provideOkHttpClient(cache, new CacheInterceptor(context));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(client)
                .build();

        T service = retrofit.create(clazz);

        return service;
    }
}
