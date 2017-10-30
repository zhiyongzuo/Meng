package com.example.zuo81.meng.presenter.dictionary;

import android.widget.Toast;

import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.bean.DictionaryBean;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;
import com.example.zuo81.meng.ui.main.view.MainView;
import com.example.zuo81.meng.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subscribers.ResourceSubscriber;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/10/26.
 */

public class DictionaryPresenterImpl extends SupportFragment implements DictionaryPresenter {

    protected CompositeDisposable mCompositeDisposable;
    private DictionaryView view;

    @Override
    public void attachView(DictionaryView view) {
        this.view = view;
        registerEvent();
    }

    public void registerEvent() {
        addSubscribe(RXBus.getInstance().toFlowable(SearchEvent.class)
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
    public void getDictionary() {

    }

    @Override
    public List<DictionaryBean> getList(String name) {

        return null;
    }

    @Override
    public void dettachView() {
        this.view = null;
        if(mCompositeDisposable!=null) {
            mCompositeDisposable.clear();
        }
    }

    private void getSearchDictionaryData(String s) {
        Logger.d(s);
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }
}
