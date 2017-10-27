package com.example.zuo81.meng.ui.welcome;

import com.example.zuo81.meng.model.bean.WelcomeBean;

/**
 * Created by zuo81 on 2017/10/23.
 */

public interface WelcomeView {

    void showPic(WelcomeBean welcomeBean);

    void showAuthor(String authorStr);

    void jumpToMain();

}
