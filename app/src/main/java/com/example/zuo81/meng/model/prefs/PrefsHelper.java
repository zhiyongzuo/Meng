package com.example.zuo81.meng.model.prefs;

/**
 * Created by zuo81 on 2017/11/17.
 */

public interface PrefsHelper {

    int getSPNumber();

    void setSPNumber(int pageNumber);



    void setWelcomePicUrl(String url);

    String getWelcomePicUrl();



    void setCurrentSongId(long id);

    long getCurrentSongId();
}
