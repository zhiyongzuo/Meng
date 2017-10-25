package com.example.zuo81.meng.model;

import com.orhanobut.logger.Logger;

/**
 * Created by zuo81 on 2017/10/25.
 */

public class WelcomeModelImpl implements WelcomeModel {

    private String url;

    @Override
    public void getPic(OnShowPicListener onShowPicListener) {
        Logger.d("getPic");
        url = "https://wx3.sinaimg.cn/mw690/7931ffafgy1fkt9v55qqjj215o0lugu4.jpg";
        onShowPicListener.onSuccess(url);
    }
}
