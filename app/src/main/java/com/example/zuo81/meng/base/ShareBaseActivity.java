package com.example.zuo81.meng.base;

import android.app.Activity;

import com.example.zuo81.meng.base.presenter.RxPresenter;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by zuo81 on 2017/11/8.
 */

public abstract class ShareBaseActivity<T extends RxPresenter> extends Activity {

    protected T presenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detachView();
        }
    }
}
