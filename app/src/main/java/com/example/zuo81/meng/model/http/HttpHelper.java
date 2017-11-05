package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;

import io.reactivex.Flowable;

/**
 * Created by zuo81 on 2017/10/31.
 */

public interface HttpHelper {

    Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s);
}
