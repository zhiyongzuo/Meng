package com.example.zuo81.meng.base.contract.share;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;

/**
 * Created by zuo81 on 2017/11/18.
 */

public interface Share {

    interface View extends BaseView {
        void showProgress(int i);

        void hideProgress();

        void uploadSuccess(long number);
    }

    interface Presenter extends BasePresenter<View> {

        void backupPic(String path);

    }
}
