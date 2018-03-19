package com.shumei.rxretrofittest.rxretrofit.okhttp;

import android.content.Context;
import android.util.Log;

import com.shumei.rxretrofittest.rxretrofit.Utils.NetUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GavynZhang on 2017/12/4 20:31.
 */

public class CacheInterceptor implements Interceptor {

    private Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //在接口声明中设置的缓存
        String cacheControl = request.cacheControl().toString();
        if (!"".equals(cacheControl)) {
            Log.e("CacheInterceptor", "!\"\".equals(cacheControl)"+"cacheControl: "+cacheControl);
            request = request.newBuilder()
                    .removeHeader("Cache-Control")
                    .removeHeader("Pragma")
                    .header("Cache-Control", cacheControl)
                    .build();
        }

        if (!NetUtils.isConnected(mContext)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Log.e("interceptor", "Sending:   url: "+request.url()+" header: "+request.headers());

        Response response = chain.proceed(request);

        Log.e("interceptor", "receive:   "+response.headers()+" code: "+response.code());

        Response responseLatest;

        // 没网7天失效
        if (NetUtils.isConnected(mContext)) {
            Log.d("CacheInterceptor", "!NetworkUtils.isConnected()");

            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age="+0)

                    .build();
        }
        else{
            Log.d("CacheInterceptor", "NetworkUtils.isConnected()");
            int maxTime = 60 * 60 * 24 * 7;
            responseLatest = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                    .build();
        }

        return responseLatest;
    }
}
