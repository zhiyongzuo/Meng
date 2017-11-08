package com.example.zuo81.meng.base;

import android.os.Bundle;

import com.example.zuo81.meng.base.presenter.RxPresenter;
import com.example.zuo81.meng.ui.welcome.WelcomeView;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<T extends RxPresenter> extends SupportActivity {

    protected T presenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detachView();
        }
    }
}
