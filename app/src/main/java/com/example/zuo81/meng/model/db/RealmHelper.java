package com.example.zuo81.meng.model.db;

import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by zuo81 on 2017/10/27.
 */

public class RealmHelper implements DBHelper {

    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
    }

    //增加字典词汇
    public void insertDictionaryBean(RealmDictionaryBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    //删除字典词汇
    public void deleteDictionaryBean(long id) {
        RealmDictionaryBean bean = mRealm.where(RealmDictionaryBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if(bean != null) {
            bean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    public List<RealmDictionaryBean> getAllRealmDictionaryList() {
        RealmResults<RealmDictionaryBean> results = mRealm.where(RealmDictionaryBean.class).findAll();
        return mRealm.copyFromRealm(results);
    }

    public RealmDictionaryBean getFirstRealmDictionary() {
        RealmDictionaryBean results = mRealm.where(RealmDictionaryBean.class).findFirst();
        return mRealm.copyFromRealm(results);
    }
    //      Photo
    public void insertPhotoBean(RealmPhotoBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    public void deletePhotoBean(long id) {
        RealmPhotoBean bean = mRealm.where(RealmPhotoBean.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        if(bean!=null) {
            bean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    public List<RealmPhotoBean> getAllRealmPhotoList() {
        RealmResults<RealmPhotoBean> results = mRealm.where(RealmPhotoBean.class).findAll();
        return mRealm.copyFromRealm(results);
    }

    public List<RealmPhotoBean> getTwentyRealmPhotoList(int pageNumber) {
        //assert pageNumber<0;
        RealmResults<RealmPhotoBean> results = mRealm.where(RealmPhotoBean.class).findAll();
        List<RealmPhotoBean> clipResults = new ArrayList<>();
        int startNumber = results.size() - (pageNumber - 1) * 20;
        for(int n=startNumber; n>startNumber-20 & n>0; n--) {
            //如果个数是11，则results.get(10)
            clipResults.add(results.get(n-1));
        }
        return mRealm.copyFromRealm(clipResults);
    }

    public void close() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
