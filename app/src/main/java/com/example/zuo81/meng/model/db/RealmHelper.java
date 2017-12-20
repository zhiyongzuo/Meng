package com.example.zuo81.meng.model.db;

import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.model.bean.realm.RealmQNMusicBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

import static com.example.zuo81.meng.app.Constants.KEY_REALM_DB_NAME;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_PIC_NUMBER_KEY;

/**
 * Created by zuo81 on 2017/10/27.
 */

public class RealmHelper implements DBHelper {

    private Realm mRealm;

    @Inject
    public RealmHelper() {
        mRealm = Realm.getDefaultInstance();
        /*mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                //.deleteRealmIfMigrationNeeded()
                .migration(new CustomMigration())
                .name(KEY_REALM_DB_NAME)
                .build());*/
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


    //music
    @Override
    public long getMusicDBSizeForId() {
        RealmResults<RealmQNMusicBean> results = mRealm.where(RealmQNMusicBean.class).findAll();
        return results.size();
    }

    @Override
    public void insertMusicBean(RealmQNMusicBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    @Override
    public boolean isMusicAlreadyUploadToQN(String name) {
        List<RealmQNMusicBean> bean = mRealm.where(RealmQNMusicBean.class).equalTo("mMusicName", name).findAll();
        if(bean.size()>0) {
            return true;
        }
        return false;
    }

    //      Photo
    @Override
    public boolean isIdExists(long id) {
        RealmPhotoBean bean = mRealm.where(RealmPhotoBean.class).equalTo("id", id).findFirst();
        return bean!=null;
    }

    public void insertPhotoBean(RealmPhotoBean bean) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(bean);
        mRealm.commitTransaction();
    }

    @Override
    public void deletePhotoBean(RealmPhotoBean mRealmPhotoBean) {
        RealmPhotoBean bean = mRealm.where(RealmPhotoBean.class).equalTo("id", mRealmPhotoBean.getId()).findFirst();
        mRealm.beginTransaction();
        if(bean!=null) {
            bean.deleteFromRealm();
        }
        mRealm.commitTransaction();
    }

    @Override
    public List<RealmPhotoBean> getAllRealmPhotoList() {
        RealmResults<RealmPhotoBean> results = mRealm.where(RealmPhotoBean.class).findAll();
        return mRealm.copyFromRealm(results);
    }

    @Override
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

    @Override
    public long getPicDBSizeForId() {
        RealmResults<RealmPhotoBean> results = mRealm.where(RealmPhotoBean.class).findAll();
        return results.size();
    }



    public void closeRealm() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
