package com.example.zuo81.meng.base.contract.main;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.WelcomeBean;

/**
 * Created by zuo81 on 2017/10/24.
 */

public interface MainContract {

    interface View extends BaseView {
        void toastSuccess(String string);
    }


    interface Presenter extends BasePresenter<View> {
        void backupRealmDB();
        void restoreRealmDB();
    }
}
