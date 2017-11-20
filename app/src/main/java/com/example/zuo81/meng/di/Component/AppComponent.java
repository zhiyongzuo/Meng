package com.example.zuo81.meng.di.Component;

import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.di.module.AppModule;
import com.example.zuo81.meng.di.module.HttpModule;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.model.http.HttpHelper;
import com.example.zuo81.meng.model.http.RetrofitHelper;
import com.example.zuo81.meng.model.prefs.ImplPrefsHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zuo81 on 2017/11/17.
 */
@Singleton
@Component(modules={AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();

    RetrofitHelper getHttpHelper();

    RealmHelper getRealmHelper();

    ImplPrefsHelper getImplPrefsHelper();

    DataManager getDataManager();
}
