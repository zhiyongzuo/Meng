package com.example.zuo81.meng.model.prefs;

import android.content.SharedPreferences;

import com.example.zuo81.meng.app.App;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_NUMBER_KEY;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_PAGE_NUMBER_KEY;
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
        sp.edit().putInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, pageNumber).apply();

    }

    @Override
    public int getSPNumber() {
        return sp.getInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, 1);
    }




    @Override
    public void setSPId(long i) {
        sp.edit().putLong(SHAREDPREFERENCES_NUMBER_KEY, i + 1).apply();
    }

    @Override
    public long getSPId() {
        return  sp.getLong(SHAREDPREFERENCES_NUMBER_KEY, 0);
    }
}


