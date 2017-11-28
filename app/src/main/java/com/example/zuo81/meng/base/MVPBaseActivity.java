package com.example.zuo81.meng.base;

import android.app.Activity;
import android.os.Bundle;

import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.di.Component.ActivityComponent;
import com.example.zuo81.meng.di.Component.AppComponent;
import com.example.zuo81.meng.di.Component.DaggerActivityComponent;

import javax.inject.Inject;

/**
 * Created by zuo81 on 2017/11/8.
 */

public abstract class MVPBaseActivity<T extends RxBasePresenter> extends NoMVPBaseActivity implements BaseView {

    @Inject
    protected T presenter;

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initInject();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detachView();
        }
    }

    public abstract void initInject();
}
