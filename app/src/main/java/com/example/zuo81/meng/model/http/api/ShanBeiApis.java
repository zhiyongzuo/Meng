package com.example.zuo81.meng.model.http.api;

import com.example.zuo81.meng.model.bean.DictionaryBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zuo81 on 2017/10/26.
 */

public interface ShanBeiApis {

    String Host = "https://api.shanbay.com/bdc/";

    @GET("search/")
    Observable<DictionaryBean> getDictionaryInfo(@Query("word") String word);
}
