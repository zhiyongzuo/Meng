package com.example.zuo81.meng.presenter.share;

import com.example.zuo81.meng.base.contract.share.Share;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.widget.CommonSubscriber;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;
import org.reactivestreams.Subscriber;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.example.zuo81.meng.app.Constants.TEST_DOMAIN;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

/**
 * Created by zuo81 on 2017/11/6.
 */

public class SharePresenter extends RxBasePresenter<Share.View> implements Share.Presenter {
    private String key;
    private long i;
    private DataManager mDataManager;

    @Inject
    public SharePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void backupPic(final String path) {
        view.showProgress(0);
        i = mDataManager.getPicDBSizeForId() + 1;
        while(mDataManager.isIdExists(i)) {
            i = i + 1;
        }
        key = "alwaysblue" + i + ".png";
        insertIntoPicUrlDB();
        addToCompositeDisposable(Observable
                .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {
                                insertIntoQN(path, key, e);
                            }
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        view.hideProgress();
                        view.uploadSuccess(i);
                    }
                })
        );
    }

    private void insertIntoPicUrlDB() {
        RealmPhotoBean bean = new RealmPhotoBean();
        bean.setId(i);
        Logger.d(key);
        bean.setPhotoUrl(TEST_DOMAIN + key);
        mDataManager.insertPhotoBean(bean);
    }

    private void insertIntoQN(String path, String key,final ObservableEmitter<String> e) {
        getUploadManagerInstance().put(new File(path), key, getUpToken(key),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.isOK()) {
                            Logger.d("complete");
                            e.onNext("1");
                        } else {
                            Logger.d("failed");
                        }
                    }
                },
                new UploadOptions(null, null, false,
                        new UpProgressHandler() {
                            @Override
                            public void progress(String key, double percent) {
                                //Logger.d("progress");
                                view.showProgress((int)percent);
                            }
                        },
                        new UpCancellationSignal() {
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
