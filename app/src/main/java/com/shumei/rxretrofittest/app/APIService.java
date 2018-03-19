package com.shumei.rxretrofittest.app;

import com.shumei.rxretrofittest.bean.Latest;
import com.shumei.rxretrofittest.bean.Story;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GavynZhang on 2017/11/27 20:56.
 */

public interface APIService {

    @GET("latest")
    Observable<Latest> getLatestNews();

    @GET("{newsId}")
    Observable<Story> getStory(@Path("newsId") String newsId);
}
