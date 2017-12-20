package com.example.zuo81.meng.presenter.music;

import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.base.contract.music.SearchMusic;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by zuo81 on 2017/11/2.
 */

public class SearchMusicPresenter extends RxBasePresenter<SearchMusic.View> implements SearchMusic.Presenter {
    private DataManager mDataManager;

    @Inject
    public SearchMusicPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SearchMusic.View view) {
        super.attachView(view);
        registerEvent();
    }

    public void registerEvent() {
        addToCompositeDisposable(RXBus.getInstance().toFlowable(SearchEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<SearchEvent>(){
                    @Override
                    public boolean test(SearchEvent searchEvent) throws Exception {
                        return searchEvent.getType() == Constants.SEARCH_TYPE_PLAYED_MUSIC;
                    }
                })
                .map(new Function<SearchEvent, String>() {
                    @Override
                    public String apply(SearchEvent searchEvent) throws Exception {
                        return searchEvent.getQuery();
                    }
                })
                .subscribeWith(new ResourceSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        getMusic(s);
                        Logger.d(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                }));
    }

    public void getMusic(String s) {
        addToCompositeDisposable(mDataManager.searchMusicListInfo(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResourceSubscriber<BaiDuMusicSearchBean>() {
                    @Override
                    public void onNext(BaiDuMusicSearchBean baiDuBeanList) {
                        Logger.d("test");
                        view.showContent(baiDuBeanList.getSong_list());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Logger.d("onError" + t.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Logger.d("oncomplete");
                    }
                }));
    }
}
