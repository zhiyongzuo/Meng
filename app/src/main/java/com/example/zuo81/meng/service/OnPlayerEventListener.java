package com.example.zuo81.meng.service;


import com.example.zuo81.meng.model.bean.music.LocalMusicBean;

/**
 * 播放进度监听器
 * Created by hzwangchenyan on 2015/12/17.
 */
public interface OnPlayerEventListener {

    void status_play();

    void status_pause();

    void showUI(LocalMusicBean bean);
}
