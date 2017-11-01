package com.example.zuo81.meng.model;

import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.http.HttpHelper;

import io.reactivex.Flowable;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class DataManager implements HttpHelper {
    private HttpHelper httpHeper;

    public DataManager(HttpHelper httpHeper) {
        this.httpHeper = httpHeper;
    }

    @Override
    public Flowable<ShanBeiBean> fetchSearchListInfo(String s) {
        return httpHeper.fetchSearchListInfo(s);
    }
}
