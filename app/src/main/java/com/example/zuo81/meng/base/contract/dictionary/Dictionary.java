package com.example.zuo81.meng.base.contract.dictionary;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/17.
 */

public interface Dictionary {

    interface View extends BaseView {

        void addItem(ShanBeiBean bean);
    }

    interface Presenter extends BasePresenter<View> {

        List<RealmDictionaryBean> getAllRealmDictionary();

        RealmDictionaryBean addToRealmDictionary(String english, String chinese);

    }
}
