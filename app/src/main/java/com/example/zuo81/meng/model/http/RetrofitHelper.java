package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.ShanBeiBean;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.orhanobut.logger.Logger;

import io.reactivex.Flowable;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class RetrofitHelper implements HttpHelper {

    private ShanBeiApis mShanBeiApis;

    public RetrofitHelper(ShanBeiApis shanBeiApis) {
        mShanBeiApis = shanBeiApis;
    }

    @Override
    public Flowable<ShanBeiBean> fetchSearchListInfo(String s) {
        Logger.d("retrofit");
        return mShanBeiApis.getDictionaryInfo(s);
    }
}
