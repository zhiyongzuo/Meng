package com.example.zuo81.meng.app;

import android.app.Activity;
import android.app.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zuo81 on 2017/10/23.
 */

public class App extends Application {
    private static App instance;
    private Set<Activity> allActivities;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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


}
