package com.example.zuo81.meng.model.db;

import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.model.bean.realm.RealmQNMusicBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/10/27.
 */

public interface DBHelper {
    //close realm
    void closeRealm();

//Gallery
    List<RealmPhotoBean> getAllRealmPhotoList();

    List<RealmPhotoBean> getTwentyRealmPhotoList(int pageNumber);

    void insertPhotoBean(RealmPhotoBean bean);

    long getPicDBSizeForId();

    /*
    *music
     */

    long getMusicDBSizeForId();

    void insertMusicBean(RealmQNMusicBean bean);

    boolean isMusicAlreadyUploadToQN(String name);

//Dictionary
    List<RealmDictionaryBean> getAllRealmDictionaryList();

    void insertDictionaryBean(RealmDictionaryBean bean);

    void deleteDictionaryBean(long id);
}
