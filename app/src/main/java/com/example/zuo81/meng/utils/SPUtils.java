package com.example.zuo81.meng.utils;

import android.content.SharedPreferences;

import com.example.zuo81.meng.app.App;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zuo81.meng.app.Constants.DICTIONARY_FRAGMENT;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_CURRENT_SONG_ID;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_FIRST_CHOSED_FRAGMENT_ID;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_XML_NAME;


public class SPUtils {
    private SharedPreferences sp;

    private SPUtils() {
        if (sp == null) {
            sp = App.getInstance().getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE);
        }
    }

    public static class Holder {
        static SPUtils prefs = new SPUtils();
    }

    public static SPUtils getInstance() {
        return Holder.prefs;
    }





    public static void setCurrentSongId(long id) {
        SPUtils.getInstance().sp.edit().putLong(SHAREDPREFERENCES_CURRENT_SONG_ID, id).apply();
    }

    public static long getCurrentSongId() {
        return SPUtils.getInstance().sp.getLong(SHAREDPREFERENCES_CURRENT_SONG_ID, 0);
    }

    public static void setFirstLoadFragment(int i) {
        SPUtils.getInstance().sp.edit().putInt(SHAREDPREFERENCES_FIRST_CHOSED_FRAGMENT_ID, i).apply();
    }

    public static int getFirstLoadFragmentItemNumber() {
        return SPUtils.getInstance().sp.getInt(SHAREDPREFERENCES_FIRST_CHOSED_FRAGMENT_ID, DICTIONARY_FRAGMENT);
    }
}
