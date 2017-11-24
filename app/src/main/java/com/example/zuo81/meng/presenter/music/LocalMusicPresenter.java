package com.example.zuo81.meng.presenter.music;

import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.base.contract.music.LocalMusic;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.utils.MusicUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by zuo81 on 2017/11/18.
 */

public class LocalMusicPresenter extends RxBasePresenter<LocalMusic.View> implements LocalMusic.Presenter {

    private DataManager mDataManager;

    @Inject
    public LocalMusicPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadMusicList() {
        addToCompositeDisposable(Observable.create(new ObservableOnSubscribe<List<LocalMusicBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<LocalMusicBean>> e) throws Exception {
                e.onNext(MusicUtils.scanMusic(App.getInstance()));
            }
        }).subscribe(new Consumer<List<LocalMusicBean>>() {
            @Override
            public void accept(List<LocalMusicBean> list) throws Exception {
                MusicUtils.getLocalMusicList().clear();
                MusicUtils.getLocalMusicList().addAll(list);
                view.hideProgress();
            }
        }));
    }
}
