package com.example.zuo81.meng.base.contract.welcome;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.WelcomeBean;

/**
 * Created by zuo81 on 2017/11/18.
 */

public interface Welcome {

    interface View extends BaseView {
        void showPic(WelcomeBean welcomeBean);
        void showAuthor(String authorStr);
        void jumpToMain();
    }

    interface Presenter extends BasePresenter<View> {


        void loadWelcomePic();
    }
}
