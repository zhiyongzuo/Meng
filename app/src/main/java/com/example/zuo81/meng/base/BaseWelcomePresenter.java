package com.example.zuo81.meng.base;

/**
 * Created by zuo81 on 2017/10/24.
 */

public class BaseWelcomePresenter<T> {
    public T view;

    public void attachView(T view) {
        this.view = view;

    }

    public void detachView() {
        this.view = null;
    }
}
