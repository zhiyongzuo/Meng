package com.example.zuo81.meng.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.App;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_CURRENT_SONG_ID;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_XML_NAME;


public class Preferences {

    private static SharedPreferences getPreferences() {
        return App.getInstance().getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE);
    }

    public static void setCurrentSongId(long id) {
        getPreferences().edit().putLong(SHAREDPREFERENCES_CURRENT_SONG_ID, id).apply();
    }

    public static long getCurrentSongId() {
        return getPreferences().getLong(SHAREDPREFERENCES_CURRENT_SONG_ID, 0);
    }
}
