package com.example.zuo81.meng.di.Component;

import com.example.zuo81.meng.di.scope.ActivityScope;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.example.zuo81.meng.ui.share.SharePicActivity;
import com.example.zuo81.meng.ui.welcome.SplashActivity;

import dagger.Component;

/**
 * Created by zuo81 on 2017/11/18.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(SharePicActivity activity);
}
