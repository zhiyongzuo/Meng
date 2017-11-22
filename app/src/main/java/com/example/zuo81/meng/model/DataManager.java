package com.example.zuo81.meng.model;

import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.db.DBHelper;
import com.example.zuo81.meng.model.http.HttpHelper;
import com.example.zuo81.meng.model.prefs.PrefsHelper;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class DataManager implements HttpHelper, DBHelper, PrefsHelper {
    private HttpHelper mHttpHelper;
    private DBHelper dbHelper;
    private PrefsHelper prefsHelper;


    public DataManager(HttpHelper httpHelper, DBHelper dbHelper, PrefsHelper prefsHelper) {
        this.mHttpHelper = httpHelper;
        this.dbHelper = dbHelper;
        this.prefsHelper = prefsHelper;
    }

    //Welcome Pic


    @Override
    public Flowable<ResponseBody> fetchPicFromUrl(String url) {
        return mHttpHelper.fetchPicFromUrl(url);
    }

    @Override
    public void setWelcomePicUrl(String url) {
        prefsHelper.setWelcomePicUrl(url);
    }

    @Override
    public String getWelcomePicUrl() {
        return prefsHelper.getWelcomePicUrl();
    }

    @Override
    public Flowable<WelcomeBean> fetchWelcomePicBean() {
        return mHttpHelper.fetchWelcomePicBean();
    }

    //ShanBei
    @Override
    public Flowable<ShanBeiBean> fetchShanBeiSearchInfo(String s) {
        return mHttpHelper.fetchShanBeiSearchInfo(s);
    }




    //SearchMusic
    public Flowable<List<BaiDuMusicSearchBean>> searchMusicListInfo(String s) {
        return mHttpHelper.searchMusicListInfo(s);
    }



/*       Dictionary           */
    @Override
    public List<RealmDictionaryBean> getAllRealmDictionaryList() {
        return dbHelper.getAllRealmDictionaryList();
    }

    @Override
    public void insertDictionaryBean(RealmDictionaryBean bean) {
        dbHelper.insertDictionaryBean(bean);
    }

    @Override
    public void deleteDictionaryBean(long id) {
        dbHelper.deleteDictionaryBean(id);
    }



    //        Gallery
    @Override
    public void insertPhotoBean(RealmPhotoBean bean) {
        dbHelper.insertPhotoBean(bean);
    }

    @Override
    public List<RealmPhotoBean> getAllRealmPhotoList() {
        return dbHelper.getAllRealmPhotoList();
    }

    @Override
    public List<RealmPhotoBean> getTwentyRealmPhotoList(int pageNumber) {
        return dbHelper.getTwentyRealmPhotoList(pageNumber);
    }

    @Override
    public long getSPId() {
        return dbHelper.getSPId();
    }



    //      SP
    @Override
    public void setSPNumber(int pageNumber) {
        prefsHelper.setSPNumber(pageNumber);
    }

    @Override
    public int getSPNumber() {
        return prefsHelper.getSPNumber();
    }
}
