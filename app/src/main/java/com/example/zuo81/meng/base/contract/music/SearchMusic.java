package com.example.zuo81.meng.base.contract.music;

import com.example.zuo81.meng.base.BasePresenter;
import com.example.zuo81.meng.base.BaseView;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/18.
 */

public interface SearchMusic {

    interface View extends BaseView {

        void showContent(List<BaiDuMusicSearchBean> baiDuBeanList);

        void showProcess();

        void hideProcess();


    }

    interface Presenter extends BasePresenter<View> {

    }
}
