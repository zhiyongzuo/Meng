package com.example.zuo81.meng.base;

import com.example.zuo81.meng.ui.welcome.WelcomeView;

public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements WelcomeView {

    protected T mPresenter;

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if(mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected void initInject(){}
}
