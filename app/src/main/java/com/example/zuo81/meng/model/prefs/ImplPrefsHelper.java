package com.example.zuo81.meng.model.prefs;

import android.content.SharedPreferences;

import com.example.zuo81.meng.app.App;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_CURRENT_SONG_ID;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_FILTER_SIZE;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_FILTER_TIME;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_PIC_NUMBER_KEY;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_GALLERY_PAGE_NUMBER_KEY;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_WELCOME_PIC_URL;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_XML_NAME;

/**
 * Created by zuo81 on 2017/11/17.
 */

public class ImplPrefsHelper implements PrefsHelper {

    private final SharedPreferences sp;

    @Inject
    public ImplPrefsHelper() {
        sp = App.getInstance().getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE);
    }

    @Override
    public void setSPNumber(int pageNumber) {
        sp.edit().putInt(SHAREDPREFERENCES_GALLERY_PAGE_NUMBER_KEY, pageNumber).apply();

    }

    @Override
    public int getSPNumber() {
        return sp.getInt(SHAREDPREFERENCES_GALLERY_PAGE_NUMBER_KEY, 1);
    }

    @Override
    public void setWelcomePicUrl(String url) {
        sp.edit().putString(SHAREDPREFERENCES_WELCOME_PIC_URL, url).apply();
    }

    @Override
    public String getWelcomePicUrl() {
        return sp.getString(SHAREDPREFERENCES_WELCOME_PIC_URL, "");
    }


    @Override
    public void setCurrentSongId(long id) {
        sp.edit().putLong(SHAREDPREFERENCES_CURRENT_SONG_ID, id).apply();
    }

    @Override
    public long getCurrentSongId() {
        return sp.getLong(SHAREDPREFERENCES_CURRENT_SONG_ID, 0);
    }
}


