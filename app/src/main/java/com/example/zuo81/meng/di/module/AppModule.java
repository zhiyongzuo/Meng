package com.example.zuo81.meng.di.module;

import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.db.DBHelper;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.model.http.HttpHelper;
import com.example.zuo81.meng.model.http.RetrofitHelper;
import com.example.zuo81.meng.model.prefs.ImplPrefsHelper;
import com.example.zuo81.meng.model.prefs.PrefsHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zuo81 on 2017/11/17.
 */
@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Singleton
    @Provides
    App provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    HttpHelper provideHttpHelper(RetrofitHelper mRetrofitHelper) {
        return mRetrofitHelper;
    }

    @Singleton
    @Provides
    DBHelper provideDBHelper(RealmHelper mRealmHelper) {
        return mRealmHelper;
    }

    @Singleton
    @Provides
    PrefsHelper providePrefsHelper(ImplPrefsHelper mImplPrefsHelper) {
        return mImplPrefsHelper;
    }

    @Singleton
    @Provides
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper dbHelper, PrefsHelper prefsHelper) {
        return new DataManager(httpHelper, dbHelper, prefsHelper);
    }
}