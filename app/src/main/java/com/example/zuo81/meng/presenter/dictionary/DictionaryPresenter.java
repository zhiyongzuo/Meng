package com.example.zuo81.meng.presenter.dictionary;

import com.example.zuo81.meng.model.bean.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.ShanBeiBean;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;

import java.util.List;

/**
 * Created by zuo81 on 2017/10/26.
 */

public interface DictionaryPresenter {

    void attachView(DictionaryView view);

    void dettachView();

    List<RealmDictionaryBean> getAllRealmDictionary();

    RealmDictionaryBean getFirstRealmDictionary();

    void addToRealmDictionary(String english, String chinese);

}
