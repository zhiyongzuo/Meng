package com.example.zuo81.meng.base;

import com.example.zuo81.meng.ui.welcome.WelcomeView;

/**
 * Created by zuo81 on 2017/10/24.
 */

public interface BasePresenter<T extends WelcomeView>{

    void attachView(T view);

    void detachView();
}
