package com.example.zuo81.meng.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.di.Component.DaggerFragmentComponent;
import com.example.zuo81.meng.di.Component.FragmentComponent;

import javax.inject.Inject;


/**
 * Created by zuo81 on 2017/10/31.
 */

public abstract class MVPBaseFragment<T extends RxBasePresenter> extends NoMVPBaseFragment implements BaseView {

    @Inject
    protected T presenter;

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .build();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        presenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if(presenter != null) {
            presenter.detachView();
        }
        super.onDestroyView();
    }

    protected abstract void initInject();
}
