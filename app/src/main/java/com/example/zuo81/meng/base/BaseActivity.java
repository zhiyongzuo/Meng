package com.example.zuo81.meng.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.zuo81.meng.R;

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements BaseView {


    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
    }

    @Override
    public void showNightMode(boolean isNight) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    protected void initInject(){}
}
