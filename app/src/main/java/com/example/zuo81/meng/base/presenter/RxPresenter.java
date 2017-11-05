package com.example.zuo81.meng.base.presenter;

import android.view.View;

import com.example.zuo81.meng.presenter.dictionary.DictionaryPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by zuo81 on 2017/11/2.
 */

public abstract class RxPresenter<T> {
    protected T view;
    protected CompositeDisposable mCompositeDisposable;

    protected void attachView(T view) {
        this.view = view;
    }

    protected void addToCompositeDisposable(Disposable disposable) {
        if(mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void detachView() {
        view = null;
        if(mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
