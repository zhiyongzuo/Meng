package com.example.zuo81.meng.presenter.welcome;

import android.text.TextUtils;

import com.example.zuo81.meng.model.WelcomeModel;
import com.example.zuo81.meng.model.WelcomeModelImpl;
import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.http.api.ShanBeiApis;
import com.example.zuo81.meng.model.http.api.WelcomeApis;
import com.example.zuo81.meng.presenter.welcome.WelcomePresenter;
import com.example.zuo81.meng.ui.welcome.WelcomeView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuo81 on 2017/10/25.
 */

public class WelcomePresenterImp implements WelcomePresenter, WelcomeModel.OnShowPicListener {

    private WelcomeView mWelcomeView;
    private WelcomeModel mWelcomeModel;
    private WelcomeApis api;
    private CompositeDisposable mCompositeDisposable;

    public WelcomePresenterImp(WelcomeView welcomeView) {
        this.mWelcomeView = welcomeView;
        mWelcomeModel = new WelcomeModelImpl();
    }

    @Override
    public void loadWelcomePic() {
        if(mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        //mCompositeDisposable.add();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WelcomeApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(WelcomeApis.class);
        api.getWelcomeInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WelcomeBean>() {
                    @Override
                    public void accept(WelcomeBean welcomeBean) throws Exception {
                        Logger.d("1");
                        mWelcomeView.showPic(welcomeBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WelcomeBean>() {
                    @Override
                    public void accept(WelcomeBean welcomeBean) throws Exception {
                        Logger.d("2");
                        mWelcomeView.jumpToMain();
                    }
                });
        //mWelcomeModel.getPic(this);
    }


    @Override
    public void onSuccess(String url) {
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onDestroy() {
        mWelcomeView = null;
        mWelcomeModel = null;
    }
}
