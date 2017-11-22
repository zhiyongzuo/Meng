package com.example.zuo81.meng.model.http.api;

import com.example.zuo81.meng.model.bean.WelcomeBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by zuo81 on 2017/10/26.
 */

public interface WelcomeApis {
    //http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN
    String HOST = "http://www.bing.com/";

    @GET("/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN")
    Flowable<WelcomeBean> getWelcomeInfo();

    @GET
    Flowable<ResponseBody> downloadWelcomePicWithDynamicUrl(@Url String url);
}
