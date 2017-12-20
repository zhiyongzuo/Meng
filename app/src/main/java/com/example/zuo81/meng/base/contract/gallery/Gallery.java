package com.example.zuo81.meng.base.contract.gallery;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/17.
 */

public interface Gallery {

    interface View extends BaseView {
        void loadMore(List<RealmPhotoBean> list);

        void refresh(List<RealmPhotoBean> list);

        void jump(List<RealmPhotoBean> list);

        void showContent(List<RealmPhotoBean> list);

    }

    interface Presenter extends BasePresenter<View> {
        void getData();

        void delete(RealmPhotoBean bean);
    }
}
