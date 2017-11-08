package com.example.zuo81.meng.presenter.dictionary;

import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.base.presenter.RxPresenter;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.ApiClient;
import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;
import com.example.zuo81.meng.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.ResourceSubscriber;

import static com.example.zuo81.meng.model.http.api.ShanBeiApis.Host;

/**
 * Created by zuo81 on 2017/10/26.
 */

public class DictionaryPresenterImpl extends RxPresenter<DictionaryView> implements DictionaryPresenter {

    private ShanBeiApis mShanBeiApis;
    private RealmHelper mRealmHelper;

    @Override
    public void attachView(DictionaryView view) {
        super.attachView(view);
        //mDataManager = new DataManager(this);
        mShanBeiApis = ApiClient.retrofit(Host).create(ShanBeiApis.class);
        mRealmHelper = new RealmHelper();
        registerEvent();
    }

    public void registerEvent() {
        addToCompositeDisposable(RXBus.getInstance().toFlowable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Predicate<SearchEvent>() {
                    @Override
                    public boolean test(SearchEvent searchEvent) throws Exception {
                        return searchEvent.getType() == Constants.TYPE_DICTIONARY;
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
                    public void onError(Throwable t) {
                    Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }

                    @Override
                    public void onNext(String s) {
                        getSearchDictionaryData(s);
                    }
                }));
    }

    @Override
    public List<RealmDictionaryBean> getAllRealmDictionary() {
        Logger.d("getAllRealmDictionary");
        return mRealmHelper.getAllRealmDictionaryList();
    }

    @Override
    public void addToRealmDictionary(String english, String chinese) {
        Logger.d("addToRealmDictionary");
        RealmDictionaryBean bean = new RealmDictionaryBean();
        Long tempmID = UUID.randomUUID().getLeastSignificantBits();
        bean.setId(tempmID);
        bean.setEnglish(english);
        bean.setChinese(chinese);
        mRealmHelper.insertDictionaryBean(bean);
    }

    public void deleteDictionaryData(long id) {
        mRealmHelper.deleteDictionaryBean(id);
    }

    @Override
    public void detachView() {
        super.detachView();
        mRealmHelper.close();
    }

    private void getSearchDictionaryData(String s) {
        Logger.d(s);
        Flowable<ShanBeiBean> observable = mShanBeiApis.getDictionaryInfo(s);
        addToCompositeDisposable(observable
                .compose(RxUtil.<ShanBeiBean>rxSchedulerHelper())
                .subscribeWith(new ResourceSubscriber<ShanBeiBean>() {

                    @Override
                    public void onError(Throwable t) {
                        Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("oncomplete");
                    }

                    @Override
                    public void onNext(ShanBeiBean shanBeiBean) {
                        Logger.d(shanBeiBean.getData().getDefinition());
                        view.updateList(shanBeiBean);
                    }
                }));
    }
}
