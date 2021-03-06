package com.example.zuo81.meng.app;

import android.os.Environment;

import com.example.zuo81.meng.R;

import java.io.File;
import java.nio.charset.Charset;

import io.realm.Realm;

/**
 * Created by zuo81 on 2017/10/23.
 */

public class Constants {
    public static final String MAP = "MAP";
    public static final int SEARCH_TYPE_DICTIONARY = 106;
    public static final int SEARCH_TYPE_MAIN_MUSIC = 16;
    public static final int SEARCH_TYPE_GALLERY = 416;
    public static final int SEARCH_TYPE_LOCALE_MUSIC = 0;
    public static final int SEARCH_TYPE_PLAYED_MUSIC = 1;
    public static final int SEARCH_TYPE_PLAY_MUSIC = 4646;
    public static final String AppID = "wxaa680ac384dde5a0";
    public static final String AppSecret = "d0eb138e6d0fb487029220df921ce8b5";
    public static final String ak = "ih2IN0jLOsBO8gT2hHcuYg6FWF51MX0_JSh2nNYX";
    public static final String sk = "8p-guROmUJVH5PaPm2CNU4Q5JQZeJ6I0C7Kfi3Kf";
    public static final String BUCKET_NAME = "alwaysblue";
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final String TEST_DOMAIN = "http://7xu8tp.com1.z0.glb.clouddn.com/";
    public static final String SHAREDPREFERENCES_XML_NAME = "34636";
    public static final String SHAREDPREFERENCES_PIC_NUMBER_KEY = "4326457347";
    public static final String SHAREDPREFERENCES_GALLERY_PAGE_NUMBER_KEY = "3257347";
    public static final String SHAREDPREFERENCES_WELCOME_PIC_URL = "3252352342347347";
    public static final String SHAREDPREFERENCES_FILTER_SIZE = "3252352DGGSSS342347347";
    public static final String SHAREDPREFERENCES_FILTER_TIME = "DFS3252352DGGSSS342347347";
    public static final String SHAREDPREFERENCES_CURRENT_SONG_ID = "DFDF3S3252352DGGSSS342347347";
    public static final String SHAREDPREFERENCES_FIRST_CHOSED_FRAGMENT_ID = "DFDFSD3S32GGSGSGSGSDG52352DGGSSS342347347";
    public static final int DICTIONARY_FRAGMENT = 0;
    public static final int MUSIC_FRAGMENT = 1;
    public static final int GALLERY_FRAGMENT = 2;

    public static final String DETAIL_FRAGMENT_PIC_POSITION = "34";
    public static final String DETAIL_FRAGMENT_PIC_URL = "33retg4";
    public static final String DETAIL_FRAGMENT_PIC_LIST = "6967";

    public static final String DATA = "data";
    public static final String NET_CACHE = "NetCache";
    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + DATA;

    public static final String PATH_CACHE = PATH_DATA + File.separator + DATA +  NET_CACHE;
    public static final String SPLASH_PIC_DIRECTORY_NAME = "pic";
    public static final String SPLASH = "splash.png";

    public static final String EXTRA_NAME_DETAIL_PIC_ACTIVITY = "FLJLW4TJ4523";

    public static final String APP_DIRECTORY = Environment.getExternalStorageDirectory() +File.separator + App.getInstance().getApplicationContext().getString(R.string.app_name) + File.separator;


    public static final String KEY_REALM_DB_NAME = "default.realm";
    public static final String DB_PATH = Realm.getDefaultInstance().getPath();

}
