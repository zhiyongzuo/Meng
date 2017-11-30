package com.example.zuo81.meng.presenter.setting;

import com.example.zuo81.meng.base.contract.setting.SettingContact;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;

import javax.inject.Inject;

/**
 * Created by zuo81 on 2017/11/30.
 */

public class SettingPresenter extends RxBasePresenter<SettingContact.View> {
    private DataManager dataManager;

    @Inject
    public SettingPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }
}
