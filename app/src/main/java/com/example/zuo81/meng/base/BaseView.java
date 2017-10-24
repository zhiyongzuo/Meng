package com.example.zuo81.meng.base;

/**
 * Created by zuo81 on 2017/10/23.
 */

public interface BaseView {

    void showErrorMsg(String msg);

    void showNightMode(boolean isNight);

    //======   state   =========

    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
