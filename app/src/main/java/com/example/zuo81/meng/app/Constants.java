package com.example.zuo81.meng.app;

import android.os.Environment;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Created by zuo81 on 2017/10/23.
 */

public class Constants {

    public static final int TYPE_DICTIONARY = 106;
    public static final int TYPE_SEARCH_MUSIC = 16;
    public static final int TYPE_PLAY_MUSIC = 4646;
    public static final String ak = "ih2IN0jLOsBO8gT2hHcuYg6FWF51MX0_JSh2nNYX";
    public static final String sk = "8p-guROmUJVH5PaPm2CNU4Q5JQZeJ6I0C7Kfi3Kf";
    public static final String BUCKET_NAME = "alwaysblue";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String TEST_DOMAIN = "http://7xu8tp.com1.z0.glb.clouddn.com/";
    public static final String SHAREDPREFERENCES_XML_NAME = "34636";
    public static final String SHAREDPREFERENCES_PIC_NUMBER_KEY = "4326457347";
    public static final String SHAREDPREFERENCES_GALLERY_PAGE_NUMBER_KEY = "3257347";
    public static final String SHAREDPREFERENCES_WELCOME_PIC_URL = "3252352342347347";
    public static final String DETAIL_FRAGMENT_PIC_POSITION = "34";
    public static final String DETAIL_FRAGMENT_PIC_URL = "33retg4";
    public static final String DETAIL_FRAGMENT_PIC_LIST = "6967";

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Meng" + File.separator + "IMG";
    public static final String SPLASH = "splash.png";

    public static final String EXTRA_NAME_DETAIL_PIC_ACTIVITY = "FLJLW4TJ4523";

}
