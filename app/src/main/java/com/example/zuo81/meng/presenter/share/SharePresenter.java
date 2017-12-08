package com.example.zuo81.meng.presenter.share;

import com.example.zuo81.meng.base.contract.share.Share;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static com.example.zuo81.meng.app.Constants.TEST_DOMAIN;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

/**
 * Created by zuo81 on 2017/11/6.
 */

public class SharePresenter extends RxBasePresenter<Share.View> implements Share.Presenter {
    private String key;
    private long i;
    private boolean isInsertIntoDBed= false;
    private boolean isUploaded= false;
    private DataManager mDataManager;

    @Inject
    public SharePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void inserIntoPicUrlDB() {
        Logger.d("insertIntoPicUrlDB");
        addToCompositeDisposable(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                RealmPhotoBean bean = new RealmPhotoBean();
                bean.setId(i);
                Logger.d(key);
                bean.setPhotoUrl(TEST_DOMAIN + key);
                mDataManager.insertPhotoBean(bean);
                e.onNext("s");
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.d("insert into DB success");
                isInsertIntoDBed = true;
                if(isUploaded) {
                    view.hideProgress(i);
                }
            }
        }));
    }

    public void uploadToQinyun(String path) {
        File picFile = new File(path);
        i = mDataManager.getPicDBSizeForId() + 1;
        key = "alwaysblue" + i + ".png";
        getUploadManagerInstance().put(picFile, key, getUpToken(key)
                , new UpCompletionHandler() {
                    @Override
                    public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                        Logger.d("upload complete");
                        isUploaded = true;
                        if (isInsertIntoDBed) {
                            view.hideProgress(i);
                        }
                    }
                }, new UploadOptions(null, null, false
                        , new UpProgressHandler() {
                    @Override
                    public void progress(String s, double v) {
                        Logger.d(s + "   " + v);
                        view.showProgress((int)v * 100);
                    }
                }, new UpCancellationSignal() {
                    @Override
                    public boolean isCancelled() {
                        return false;
                    }
                }
                )
        );
    }

    @Override
    public void detachView() {
        super.detachView();
        mDataManager.closeRealm();
    }
}
