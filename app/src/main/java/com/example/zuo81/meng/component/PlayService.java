package com.example.zuo81.meng.component;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.example.zuo81.meng.utils.MusicUtils;
import com.example.zuo81.meng.utils.Preferences;
import com.xyzlf.share.library.ShareHandlerActivity;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    private boolean isPlaying;
    private boolean isPausing;
    private List<LocalMusicBean> musicListHistory = new ArrayList<>();
    private LocalMusicBean playingMusic;
    private OnPlayerEventListener listener;
    private MediaPlayer mPlayer = new MediaPlayer();
    //inject DataManager Error nullPointException  但是在dictionarypresenter中注入，正常运行。。。
    /*@Inject
    DataManager mDataManager;*/
    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //mDataManager.setCurrentSongId(1244555);
        //Logger.d(mDataManager.getCurrentSongId());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void setOnPlayerEventListener(OnPlayerEventListener listener) {
        this.listener = listener;
    }

    public void play() {
        if(isPlaying) {
            pause();
        } else if(isPausing) {

        } else {
            long id = Preferences.getCurrentSongId();
            LocalMusicBean bean = MusicUtils.queryFromId(id);
            playMusic(bean);
        }
    }

    public void playMusic(LocalMusicBean bean) {
        playingMusic = bean;
        listener.status_play();
        if (musicListHistory.indexOf(bean)==-1) {
            musicListHistory.add(bean);
        }
        Preferences.setCurrentSongId(bean.getId());
        try {
            mPlayer.reset();
            mPlayer.setDataSource(bean.getPath());
            mPlayer.prepare();
            mPlayer.start();
            isPlaying = true;
            isPausing = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        listener.status_pause();
        mPlayer.pause();
        isPausing = true;
        isPlaying = false;
    }

    public void prePlay() {

    }

    public LocalMusicBean getPlayingMusic() {
        return playingMusic;
    }

    public long getCurrentPosition() {
        if(isPlaying || isPausing) {
            return mPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public void showUI(LocalMusicBean bean) {
        listener.showUI(bean);
    }

    public void share(Activity activity) {
        long id = Preferences.getCurrentSongId();
        LocalMusicBean bean = MusicUtils.queryFromId(id);
        if (bean!=null) {
            //todo qiniu
            ShareEntity testBean = new ShareEntity(playingMusic.getTitle(), playingMusic.getArtist());
            testBean.setUrl(playingMusic.getPath()); //分享链接
            testBean.setImgUrl(playingMusic.getCoverPath());
            ShareUtil.startShare(activity, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
        } else {
            ShareEntity testBean = new ShareEntity(playingMusic.getTitle(), playingMusic.getArtist());
            testBean.setUrl(playingMusic.getPath()); //分享链接
            testBean.setImgUrl(playingMusic.getCoverPath());
            ShareUtil.startShare(activity, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }
}
