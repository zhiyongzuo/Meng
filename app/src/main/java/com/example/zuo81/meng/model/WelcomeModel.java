package com.example.zuo81.meng.model;

/**
 * Created by zuo81 on 2017/10/25.
 */

public interface WelcomeModel {

    interface OnShowPicListener {

        void onSuccess(String url);

        void onFailure();

    }

    void getPic(OnShowPicListener onShowPicListener);
}
