package com.example.zuo81.meng.presenter.gallery;

import android.content.Context;

import com.example.zuo81.meng.base.presenter.RxPresenter;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.model.db.RealmHelper;
import com.example.zuo81.meng.ui.gallery.GalleryView;
import com.orhanobut.logger.Logger;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_PAGE_NUMBER_KEY;
import static com.example.zuo81.meng.app.Constants.SHAREDPREFERENCES_XML_NAME;

/**
 * Created by zuo81 on 2017/11/9.
 */

public class GalleryPresenter extends RxPresenter<GalleryView> {
    private RealmHelper realmHelper;
    private List<RealmPhotoBean> list;
    private Context context;
    private int pageNumber = 1;

    public GalleryPresenter(GalleryView view, Context context) {
        this.context = context;
        attachView(view);
        realmHelper = new RealmHelper();
        pageNumber = context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                .getInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, 1);
        Logger.d("constractor" + pageNumber);
    }

    public List<RealmPhotoBean> getData() {
        Logger.d("getData" + pageNumber);
        if (pageNumber==1) {
            int size = realmHelper.getAllRealmPhotoList().size();
            if (size <=10) {
                list = realmHelper.getAllRealmPhotoList();
            } else {
                list = realmHelper.getTwentyRealmPhotoList(pageNumber);
            }
        } else {
            list = realmHelper.getTwentyRealmPhotoList(pageNumber);
        }
        return list;
    }

    public void refreshData() {
        context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                .edit().putInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, 1).apply();
        pageNumber = context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                .getInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, 1);
        view.refresh(realmHelper.getTwentyRealmPhotoList(pageNumber));
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
        pageNumber = context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                .getInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, 1);
        pageNumber += 1;
        if(realmHelper.getTwentyRealmPhotoList(pageNumber).size() > 0) {
            context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                    .edit().putInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, pageNumber).apply();
            Logger.d("loadMore" + pageNumber);
        }
        view.loadMore(realmHelper.getTwentyRealmPhotoList(pageNumber));
    }

    public void jumpData(int pageNumber) {
        if (realmHelper.getTwentyRealmPhotoList(pageNumber).size() > 0) {
            context.getSharedPreferences(SHAREDPREFERENCES_XML_NAME, MODE_PRIVATE)
                    .edit().putInt(SHAREDPREFERENCES_PAGE_NUMBER_KEY, pageNumber).apply();
            Logger.d("jump" + pageNumber);
        }
        view.jump(realmHelper.getTwentyRealmPhotoList(pageNumber));
    }
}
