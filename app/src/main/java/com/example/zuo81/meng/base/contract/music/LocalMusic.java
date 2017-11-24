package com.example.zuo81.meng.base.contract.music;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/18.
 */

public interface LocalMusic {

    interface View extends BaseView {
        void showContent(List<LocalMusicBean> list);
        void showProgress();
        void hideProgress();
    }

    interface Presenter extends BasePresenter<View> {
        void loadMusicList();
    }
}
