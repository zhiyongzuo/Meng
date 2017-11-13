package com.example.zuo81.meng.presenter.music;

import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.base.presenter.RxPresenter;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.ApiClient;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.model.http.RetrofitHelper;
import com.example.zuo81.meng.model.http.api.BaiDuApis;
import com.example.zuo81.meng.ui.music.view.MusicView;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by zuo81 on 2017/11/2.
 */

public class SearchMusicPresenter extends RxPresenter<MusicView> {

    RetrofitHelper helper;

    @Override
    public void attachView(MusicView view) {
        super.attachView(view);
        helper = ApiClient.retrofit(BaiDuApis.Host).create(RetrofitHelper.class);
        registerEvent();
    }

    public void registerEvent() {
        addToCompositeDisposable(RXBus.getInstance().toFlowable(SearchEvent.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<SearchEvent>(){
                    @Override
                    public boolean test(SearchEvent searchEvent) throws Exception {
                        return searchEvent.getType() == Constants.TYPE_SEARCH_MUSIC;
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
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    public void getMusic(String s) {
        addToCompositeDisposable(helper.searchMusicListInfo(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BaiDuMusicSearchBean>>() {
                    @Override
                    public void accept(List<BaiDuMusicSearchBean> baiDuBeanList) throws Exception {
                        Logger.d("test");
                        view.showContent(baiDuBeanList);
                    }
                }));
    }
}
