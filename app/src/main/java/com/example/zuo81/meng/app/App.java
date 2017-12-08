package com.example.zuo81.meng.app;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.bilibili.socialize.share.core.BiliShare;
import com.bilibili.socialize.share.core.BiliShareConfiguration;
import com.example.zuo81.meng.di.Component.AppComponent;
import com.example.zuo81.meng.di.Component
        .DaggerAppComponent;
import com.example.zuo81.meng.di.module.AppModule;
import com.example.zuo81.meng.di.module.HttpModule;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.Logger;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.HashSet;
import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;


/**
 * Created by zuo81 on 2017/10/23.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;
    private static AppComponent appComponent;
    private static BiliShareConfiguration configuration;
    private static BiliShare biliShare;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(5)
                .migration(new CustomMigration())
                .build();
        Realm.setDefaultConfiguration(config);

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

    private static BiliShareConfiguration getConfiguration() {
        if(configuration == null) {
            configuration = new BiliShareConfiguration.Builder(instance)
                    .weixin("wxaa680ac384dde5a0") //配置微信
                    //.imageDownloader(new ShareFrescoImageDownloader()) //图片下载器
                    .build();
        }
        return configuration;
    }

    public static BiliShare getBiliShare() {
        biliShare = BiliShare.global();
        biliShare.config(getConfiguration());
        return biliShare;
    }

    class CustomMigration implements RealmMigration {
        @Override
        public int hashCode() {
            return 37;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof CustomMigration);
        }

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            if(oldVersion == 0 && newVersion == 1) {
                RealmSchema schema = realm.getSchema();
                RealmObjectSchema personSchema = schema.create("RealmMusicBean");
                personSchema.addField("id", long.class);
                personSchema.addField("mMusicName", String.class);
                personSchema.addField("artistName", String.class);
                personSchema.addField("musicPath", String.class);
                oldVersion++;
            } else if(oldVersion == 1 && newVersion == 2) {
                Logger.d(oldVersion);
                Log.d("1", "" + oldVersion);
                 RealmSchema schema = realm.getSchema();
                 RealmObjectSchema personSchema = schema.get("RealmMusicBean");
                 personSchema.addField("v", String.class)
                         .transform(new RealmObjectSchema.Function() {
                             @Override
                             public void apply(DynamicRealmObject obj) {
                                 obj.set("v", "1");
                                 obj.set("id", 0);
                             }
                         });
                 oldVersion++;
            } else if(oldVersion ==2 && newVersion == 3) {
                Log.d("2", "" + oldVersion);
                RealmSchema schema = realm.getSchema();
                RealmObjectSchema personSchema = schema.get("RealmMusicBean");
                personSchema.removeField("id");
                personSchema.removeField("v");
                oldVersion++;
            } else if(oldVersion ==3 && newVersion == 4) {
                Log.d("3", "" + oldVersion);
                RealmSchema schema = realm.getSchema();
                RealmObjectSchema personSchema = schema.get("RealmMusicBean");
                personSchema.addField("id", long.class, FieldAttribute.PRIMARY_KEY);
                oldVersion++;
            } else if(oldVersion ==3 && newVersion == 4) {
                Log.d("4", "" + oldVersion);
                RealmSchema schema = realm.getSchema();
                RealmObjectSchema personSchema = schema.get("RealmMusicBean");
                personSchema.removeField("id");
                personSchema.addField("mId", long.class, FieldAttribute.PRIMARY_KEY);
                oldVersion++;
            } else if(oldVersion ==4 && newVersion == 5) {
                Log.d("4", "" + oldVersion);
                RealmSchema schema = realm.getSchema();
                schema.remove("RealmMusicBean");
                RealmObjectSchema personSchema = schema.create("RealmQNMusicBean");
                personSchema.addField("id", long.class, FieldAttribute.PRIMARY_KEY);
                personSchema.addField("mMusicName", String.class);
                personSchema.addField("artistName", String.class);
                personSchema.addField("musicPath", String.class);
                oldVersion++;
            }
        }
    }

}
