package com.example.zuo81.meng.widget;

import com.example.zuo81.meng.base.BaseView;
import com.orhanobut.logger.Logger;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by zuo81 on 2017/12/8.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView view;

    public CommonSubscriber(BaseView view) {
        this.view = view;
    }

    @Override
    public void onComplete() {
        Logger.d("onComplete");
    }

    @Override
    public void onError(Throwable t) {
        Logger.d(t.getMessage());
    }
}
