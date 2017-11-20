package com.example.zuo81.meng.base.presenter;

import android.view.View;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zuo81 on 2017/11/2.
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */

public class RxBasePresenter<T extends BaseView> implements BasePresenter<T> {
    protected T view;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    protected void addToCompositeDisposable(Disposable disposable) {
        if(mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void detachView() {
        view = null;
        if(mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
