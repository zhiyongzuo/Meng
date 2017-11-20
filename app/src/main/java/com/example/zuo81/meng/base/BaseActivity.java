package com.example.zuo81.meng.base;

import com.example.zuo81.meng.base.presenter.RxBasePresenter;

import me.yokeyword.fragmentation.SupportActivity;

public abstract class BaseActivity<T extends RxBasePresenter> extends SupportActivity {

    protected T presenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detachView();
        }
    }
}
