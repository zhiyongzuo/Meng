package com.example.zuo81.meng.base;

/**
 * Created by zuo81 on 2017/10/24.
 */

public interface BasePresenter<T extends BaseView>{

    void attachView(T view);

    void detachView();
}
