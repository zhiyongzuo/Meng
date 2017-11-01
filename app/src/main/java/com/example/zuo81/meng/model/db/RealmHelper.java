package com.example.zuo81.meng.model.db;

import com.example.zuo81.meng.model.bean.RealmDictionaryBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by zuo81 on 2017/10/27.
 */

public class RealmHelper implements DBHelper {

    private static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .schemaVersion(0)
                .build());
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

    public void close() {
        mRealm.close();
    }
}
