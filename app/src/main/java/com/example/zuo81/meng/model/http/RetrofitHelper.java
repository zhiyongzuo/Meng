package com.example.zuo81.meng.model.http;

import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.bean.music.BaiDuPlayBean;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.http.api.BaiDuApis;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.example.zuo81.meng.model.http.api.SplashApis;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

import static com.example.zuo81.meng.model.http.api.BaiDuApis.Method_play;
import static com.example.zuo81.meng.model.http.api.BaiDuApis.Method_search;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class RetrofitHelper implements HttpHelper {

    private ShanBeiApis mShanBeiApis;
    private BaiDuApis mBaiDuApis;
    private SplashApis mWelcomeApis;

    @Inject
    public RetrofitHelper(ShanBeiApis shanBeiApis, BaiDuApis baiDuApis, SplashApis welcomeApis) {
        mShanBeiApis = shanBeiApis;
        mBaiDuApis = baiDuApis;
        mWelcomeApis = welcomeApis;
    }

    @Override
    public Flowable<ResponseBody> fetchPicFromUrl(String url) {
        return mWelcomeApis.downloadWelcomePicWithDynamicUrl(url);
    }

    @Override
    public Flowable<WelcomeBean> fetchWelcomePicBean() {
        return mWelcomeApis.getWelcomeInfo();
    }

    @Override
    public Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s) {
        Logger.d("retrofit");
        return mShanBeiApis.getDictionaryInfo(s);
    }

    public Flowable<BaiDuMusicSearchBean> searchMusicListInfo(String s) {
        return mBaiDuApis.fetchMusicListInfo(Method_search, s);
    }

    public Flowable<BaiDuPlayBean> playMusicLink(String songId) {
        return mBaiDuApis.playMusicLink(Method_play, songId);
    }
}
