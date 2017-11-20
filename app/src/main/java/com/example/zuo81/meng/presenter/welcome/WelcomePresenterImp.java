package com.example.zuo81.meng.presenter.welcome;

import com.example.zuo81.meng.base.contract.welcome.Welcome;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.http.api.WelcomeApis;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zuo81 on 2017/10/25.
 */

public class WelcomePresenterImp extends RxBasePresenter<Welcome.View> implements Welcome.Presenter{

    private DataManager mDataManager;
    private WelcomeApis api;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public WelcomePresenterImp(DataManager mDataManager) {
        this.mDataManager = mDataManager;
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
                        view.showPic(welcomeBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WelcomeBean>() {
                    @Override
                    public void accept(WelcomeBean welcomeBean) throws Exception {
                        view.jumpToMain();
                    }
                });
        //mWelcomeModel.getPic(this);
    }
}
