package com.example.zuo81.meng.model.bean.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zuo81 on 2017/11/6.
 */

public class RealmPhotoBean extends RealmObject {

    @PrimaryKey
    private long id;
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
