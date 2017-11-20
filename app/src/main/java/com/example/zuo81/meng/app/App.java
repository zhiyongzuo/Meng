package com.example.zuo81.meng.app;

import android.app.Activity;
import android.app.Application;

import com.example.zuo81.meng.di.Component.AppComponent;
import com.example.zuo81.meng.di.Component.DaggerAppComponent;
import com.example.zuo81.meng.di.module.AppModule;
import com.example.zuo81.meng.di.module.HttpModule;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;


/**
 * Created by zuo81 on 2017/10/23.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;
    private static AppComponent appComponent;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        Stetho.initialize(//Stetho初始化
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        instance = this;
    }

    public void addActivity(Activity activity) {
        if(allActivities == null) {
            allActivities = new HashSet<>();
        } else {
            allActivities.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if(allActivities != null) {
            allActivities.remove(activity);
        }
    }

    public void exitApp() {
        if(allActivities != null) {
            for(Activity activity : allActivities) {
                activity.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static AppComponent getAppComponent() {
        if(appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

}
