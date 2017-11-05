package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.music.BaiDuPlayBean;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.http.api.BaiDuApis;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Flowable;

import static com.example.zuo81.meng.model.http.api.BaiDuApis.Method_play;
import static com.example.zuo81.meng.model.http.api.BaiDuApis.Method_search;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class RetrofitHelper implements HttpHelper {

    private ShanBeiApis mShanBeiApis;
    private BaiDuApis mBaiDuApis;


    @Override
    public Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s) {
        Logger.d("retrofit");
        return mShanBeiApis.getDictionaryInfo(s);
    }

    public Flowable<List<BaiDuMusicSearchBean>> searchMusicListInfo(String s) {
        return mBaiDuApis.fetchMusicListInfo(Method_search, s, "10", "json");
    }

    public Flowable<BaiDuPlayBean> playMusicLink(String songId) {
        return mBaiDuApis.playMusicLink(Method_play, songId);
    }
}
