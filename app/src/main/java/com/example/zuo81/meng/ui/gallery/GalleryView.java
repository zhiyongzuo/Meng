package com.example.zuo81.meng.ui.gallery;

import android.view.View;

import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/9.
 */

public interface GalleryView {
    void loadMore(List<RealmPhotoBean> list);

    void refresh(List<RealmPhotoBean> list);

    void jump(List<RealmPhotoBean> list);
}
