package com.example.zuo81.meng.presenter.gallery;

import com.example.zuo81.meng.base.contract.gallery.Gallery;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zuo81 on 2017/11/9.
 */

public class GalleryPresenter extends RxBasePresenter<Gallery.View> implements Gallery.Presenter {
    private DataManager mDataManager;
    private List<RealmPhotoBean> list;
    private int pageNumber = 1;

    @Inject
    public GalleryPresenter(DataManager mDataManater) {
        this.mDataManager = mDataManater;
    }

    @Override
    public List<RealmPhotoBean> getData() {
        pageNumber = mDataManager.getSPNumber();
        Logger.d("getData" + pageNumber);
        if (pageNumber==1) {
            int size = mDataManager.getAllRealmPhotoList().size();
            if (size <=10) {
                list = mDataManager.getAllRealmPhotoList();
            } else {
                list = mDataManager.getTwentyRealmPhotoList(pageNumber);
            }
        } else {
            list = mDataManager.getTwentyRealmPhotoList(pageNumber);
        }
        return list;
    }

    public void refreshData() {
        mDataManager.setSPNumber(1);
        pageNumber = mDataManager.getSPNumber();
        view.refresh(mDataManager.getTwentyRealmPhotoList(pageNumber));
        Logger.d("refreshData" + pageNumber);
        //geeknews在realm数据库请求没有使用rxjava
        /*addToCompositeDisposable(Observable.create(new ObservableOnSubscribe<List<RealmPhotoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<RealmPhotoBean>> e) throws Exception {
                e.onNext(realmHelper.getTwentyRealmPhotoList(pageNumber));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<RealmPhotoBean>>() {
            @Override
            public void accept(List<RealmPhotoBean> s) throws Exception {
                view.refresh(s);
                Logger.d("refreshData" + pageNumber);
            }
        }));*/
    }

    public void loadMoreData() {
        pageNumber = mDataManager.getSPNumber();
        Logger.d(pageNumber);
        pageNumber += 1;
        if(mDataManager.getTwentyRealmPhotoList(pageNumber).size() > 0) {
            mDataManager.setSPNumber(pageNumber);
            Logger.d("loadMore" + pageNumber);
        }
        view.loadMore(mDataManager.getTwentyRealmPhotoList(pageNumber));
    }

    public void jumpData(int pageNumber) {
        if (mDataManager.getTwentyRealmPhotoList(pageNumber).size() > 0) {
            mDataManager.setSPNumber(pageNumber);
            Logger.d("jump" + pageNumber);
        }
        view.jump(mDataManager.getTwentyRealmPhotoList(pageNumber));
    }
}
