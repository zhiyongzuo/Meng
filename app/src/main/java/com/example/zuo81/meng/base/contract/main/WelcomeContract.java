package com.example.zuo81.meng.base.contract.main;

import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.WelcomeBean;

/**
 * Created by zuo81 on 2017/10/24.
 */

public interface WelcomeContract {

    interface View extends BaseView {
        void showContent(WelcomeBean welcomeBean);

        void jumpToMain();
    }
}
