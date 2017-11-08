package com.example.zuo81.meng.presenter.share;

import com.example.zuo81.meng.base.presenter.RxPresenter;
import com.example.zuo81.meng.model.ApiClient;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.ui.share.ShareView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zuo81 on 2017/11/6.
 */

public class SharePresenter extends RxPresenter<ShareView> {
    private RealmHelper realmHelper;

    public SharePresenter(ShareView view) {
        attachView(view);
        realmHelper = new RealmHelper();
    }

    public void inserIntoPicUrlDB(final String url) {
        addToCompositeDisposable(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                RealmPhotoBean bean = new RealmPhotoBean();
                bean.setPhotoUrl(url);
                realmHelper.insertPhotoBean(bean);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                view.closeActivity();
            }
        }));
    }

    @Override
    public void detachView() {
        super.detachView();
        realmHelper.close();
    }
}
