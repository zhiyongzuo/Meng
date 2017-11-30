package com.example.zuo81.meng.component;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.utils.MusicUtils;
import com.example.zuo81.meng.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    private static boolean isPlaying;
    private static boolean isPausing;
    private static boolean isPreparing = true;
    private List<LocalMusicBean> musicListHistory = new ArrayList<>();
    private LocalMusicBean playingMusic;
    private OnPlayerEventListener listener;
    private MediaPlayer mPlayer = new MediaPlayer();
    private Handler handler = new Handler();
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

    public void buttonPlayClick(Activity activity) {
        if (isPlaying) {
            pause();
        } else if (isPausing) {
            start();
        } else if (isPreparing) {
            long id = SPUtils.getCurrentSongId();
            LocalMusicBean bean = MusicUtils.queryFromId(id);
            if (bean != null) {
                playMusic(bean);
            } else {
                Toast.makeText(activity, "无法播放，没有选中音乐", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void playMusic(LocalMusicBean bean) {
        playingMusic = bean;
        listener.status_play();
        if (musicListHistory.indexOf(bean)==-1) {
            musicListHistory.add(bean);
        }
        SPUtils.setCurrentSongId(bean.getId());
        try {
            mPlayer.reset();
            mPlayer.setDataSource(bean.getPath());
            mPlayer.prepare();
            mPlayer.start();
            isPlaying = true;
            isPausing = false;
            isPreparing = false;
            handler.post(runnable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        mPlayer.start();
        handler.post(runnable);
        isPlaying = true;
        isPausing = false;
        isPreparing = false;
        listener.status_play();
    }

    public void pause() {
        listener.status_pause();
        mPlayer.pause();
        handler.removeCallbacks(runnable);
        isPausing = true;
        isPlaying = false;
        isPreparing = false;
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
        long id = SPUtils.getCurrentSongId();
        Logger.d(id);
        LocalMusicBean bean = MusicUtils.queryFromId(id);
        if (bean!=null) {
            //todo qiniu
            shareToWX(activity, bean);
        } else {
            Logger.d("null");
        }
    }

    private void shareToWX(Activity activity, LocalMusicBean bean) {
        ShareEntity testBean = new ShareEntity(bean.getTitle(), bean.getArtist());
        testBean.setUrl(bean.getPath()); //分享链接
        testBean.setImgUrl(bean.getCoverPath());
        ShareUtil.startShare(activity, ShareConstant.SHARE_CHANNEL_WEIXIN_CIRCLE, testBean, ShareConstant.REQUEST_CODE);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Logger.d((int)MusicUtils.getPlayService().getCurrentPosition());
            listener.showSeekBarProgress((int)MusicUtils.getPlayService().getCurrentPosition());
            handler.postDelayed(this, 300);
        }
    };

    public class PlayBinder extends Binder {
        public PlayService getService() {
            return PlayService.this;
        }
    }
}
