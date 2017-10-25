package com.example.zuo81.meng.presenter;

import android.text.TextUtils;

import com.example.zuo81.meng.model.WelcomeModel;
import com.example.zuo81.meng.model.WelcomeModelImpl;
import com.example.zuo81.meng.ui.welcome.WelcomeView;
import com.orhanobut.logger.Logger;

/**
 * Created by zuo81 on 2017/10/25.
 */

public class WelcomePresenterImp implements WelcomePresenter, WelcomeModel.OnShowPicListener {

    private WelcomeView mWelcomeView;
    private WelcomeModel mWelcomeModel;

    public WelcomePresenterImp(WelcomeView welcomeView) {
        this.mWelcomeView = welcomeView;
        mWelcomeModel = new WelcomeModelImpl();
    }

    @Override
    public void loadWelcomePic() {
        mWelcomeModel.getPic(this);
    }


    @Override
    public void onSuccess(String url) {
        if (!TextUtils.isEmpty(url)) {
            mWelcomeView.showPic(url);
        }
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onDestroy() {
        mWelcomeView = null;
        mWelcomeModel = null;
    }
}
