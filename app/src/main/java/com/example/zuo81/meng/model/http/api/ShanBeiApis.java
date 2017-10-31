package com.example.zuo81.meng.model.http.api;

import com.example.zuo81.meng.model.bean.ShanBeiBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zuo81 on 2017/10/26.
 */

public interface ShanBeiApis {

    String Host = "https://api.shanbay.com/bdc/";

    @GET("search/")
    Flowable<ShanBeiBean> getDictionaryInfo(@Query("word") String word);
}
