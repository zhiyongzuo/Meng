package com.example.zuo81.meng.model.bean.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zuo81 on 2017/12/7.
 */

public class RealmQNMusicBean extends RealmObject {
    @PrimaryKey
    private long id;

    private String mMusicName;
    private String artistName;
    private String musicPath;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public String getMusicName() {
        return mMusicName;
    }

    public void setMusicName(String mMusicName) {
        this.mMusicName = mMusicName;
    }
}
