package com.example.zuo81.meng.presenter.music;

import com.example.zuo81.meng.base.contract.music.LocalMusic;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;

import javax.inject.Inject;

/**
 * Created by zuo81 on 2017/11/18.
 */

public class LocalMusicPresenter extends RxBasePresenter<LocalMusic.View> implements LocalMusic.Presenter {

    private DataManager mDataManager;

    @Inject
    public LocalMusicPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
}
