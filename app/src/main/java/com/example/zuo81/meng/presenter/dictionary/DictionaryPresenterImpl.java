package com.example.zuo81.meng.presenter.dictionary;

import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.base.contract.dictionary.Dictionary;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.ApiClient;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.example.zuo81.meng.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by zuo81 on 2017/10/26.
 */

public class DictionaryPresenterImpl extends RxBasePresenter<Dictionary.View> implements Dictionary.Presenter {
    @Inject
    DataManager mDataManager;

    @Inject
    public DictionaryPresenterImpl() {
    }

    //private DataManager mDataManager;

    /*@Inject
    public DictionaryPresenterImpl(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }*/

    @Override
    public void attachView(Dictionary.View view) {
        super.attachView(view);
        registerEvent();
    }

    public void registerEvent() {
        addToCompositeDisposable(RXBus.getInstance().toFlowable(SearchEvent.class)
                .compose(RxUtil.<SearchEvent>rxSchedulerHelper())
                .filter(new Predicate<SearchEvent>() {
                    @Override
                    public boolean test(SearchEvent searchEvent) throws Exception {
                        return searchEvent.getType() == Constants.SEARCH_TYPE_DICTIONARY;
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
        return mDataManager.getAllRealmDictionaryList();
    }

    @Override
    public RealmDictionaryBean addToRealmDictionary(String english, String chinese) {
        Logger.d("addToRealmDictionary");
        RealmDictionaryBean bean = new RealmDictionaryBean();
        Long tempmID = UUID.randomUUID().getLeastSignificantBits();
        bean.setId(tempmID);
        bean.setEnglish(english);
        bean.setChinese(chinese);
        mDataManager.insertDictionaryBean(bean);
        return bean;
    }

    public void deleteDictionaryData(long id) {
        mDataManager.deleteDictionaryBean(id);
    }

    private void getSearchDictionaryData(String s) {
        Logger.d(s);
        Flowable<ShanBeiBean> observable = mDataManager.fetchShanBeiSearchInfo(s);
        addToCompositeDisposable(observable
                .compose(RxUtil.<ShanBeiBean>rxSchedulerHelper())
                .subscribeWith(new ResourceSubscriber<ShanBeiBean>() {

                    @Override
                    public void onError(Throwable t) {
                        Logger.d("onError");
                    }

                    @Override
                    public void onComplete() {
//                        Logger.d("oncomplete");
                    }

                    @Override
                    public void onNext(ShanBeiBean shanBeiBean) {
                        Logger.d(shanBeiBean.getData().getDefinition());
                        view.addItem(shanBeiBean);
                    }
                }));
    }
}
