package com.example.zuo81.meng.presenter.welcome;

import android.text.TextUtils;

import com.example.zuo81.meng.base.contract.welcome.Welcome;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.model.http.api.WelcomeApis;
import com.example.zuo81.meng.utils.FileUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.example.zuo81.meng.app.Constants.APP_DIRECTORY;
import static com.example.zuo81.meng.app.Constants.SPLASH;
import static com.example.zuo81.meng.app.Constants.SPLASH_PIC_DIRECTORY_NAME;

/**
 * Created by zuo81 on 2017/10/25.
 */

public class WelcomePresenterImp extends RxBasePresenter<Welcome.View> implements Welcome.Presenter{
    private DataManager mDataManager;
    public boolean save;

    @Inject
    public WelcomePresenterImp(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void loadWelcomePic() {
    }

    @Override
    public void updateWelcomePic() {
        addToCompositeDisposable(mDataManager.fetchWelcomePicBean()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WelcomeBean>() {
                    @Override
                    public void accept(WelcomeBean welcomeBean) throws Exception {
                        String url = "http://www.bing.com" + welcomeBean.getImages().get(0).getUrl();
                        if (mDataManager.getWelcomePicUrl().equals(url)
                                && FileUtils.isFileExists(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH)) {
                            view.showPic();
                            save = false;
                        } else {
                            Logger.d(url);
                            mDataManager.setWelcomePicUrl(url);
                            save = true;
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .concatMap(new Function<WelcomeBean, Flowable<ResponseBody>>() {
                    @Override
                    public Flowable<ResponseBody> apply(WelcomeBean welcomeBean) throws Exception {
                        return mDataManager.fetchPicFromUrl(welcomeBean.getImages().get(0).getUrl());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody body) throws Exception {
                        if (save) {
                            view.savePic(body);
                        }
                    }
                }));
    }
}
