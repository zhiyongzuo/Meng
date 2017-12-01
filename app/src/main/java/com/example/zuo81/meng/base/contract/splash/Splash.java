package com.example.zuo81.meng.base.contract.splash;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;

import okhttp3.ResponseBody;

/**
 * Created by zuo81 on 2017/11/18.
 */

public interface Splash {

    interface View extends BaseView {
        void savePic(ResponseBody body);
        void showAuthor(String authorStr);
        void jumpToMain();
        void showPic();
    }

    interface Presenter extends BasePresenter<View> {

        void loadWelcomePic();
        void updateWelcomePic();

    }
}
