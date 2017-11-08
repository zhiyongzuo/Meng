package com.example.zuo81.meng.model.db;

import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

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

    public void close() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
