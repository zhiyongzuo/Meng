package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by zuo81 on 2017/10/31.
 */

public interface HttpHelper {

    Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s);

    Flowable<List<BaiDuMusicSearchBean>> searchMusicListInfo(String s);
}
