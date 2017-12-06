package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by zuo81 on 2017/10/31.
 */

public interface HttpHelper {

    Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s);

    Flowable<BaiDuMusicSearchBean> searchMusicListInfo(String s);

    Flowable<WelcomeBean> fetchWelcomePicBean();

    Flowable<ResponseBody> fetchPicFromUrl(String url);
}
