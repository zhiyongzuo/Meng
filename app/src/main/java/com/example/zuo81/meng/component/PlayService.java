package com.example.zuo81.meng.component;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.bilibili.socialize.share.core.SocializeListeners;
import com.bilibili.socialize.share.core.SocializeMedia;
import com.bilibili.socialize.share.core.shareparam.BaseShareParam;
import com.bilibili.socialize.share.core.shareparam.ShareAudio;
import com.bilibili.socialize.share.core.shareparam.ShareImage;
import com.bilibili.socialize.share.core.shareparam.ShareParamAudio;
import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.model.bean.realm.RealmQNMusicBean;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.utils.BitmapCache;
import com.example.zuo81.meng.utils.MusicUtils;
import com.example.zuo81.meng.utils.SPUtils;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.zuo81.meng.app.App.getBiliShare;
import static com.example.zuo81.meng.app.Constants.APP_DIRECTORY;
import static com.example.zuo81.meng.app.Constants.SPLASH;
import static com.example.zuo81.meng.app.Constants.SPLASH_PIC_DIRECTORY_NAME;
import static com.example.zuo81.meng.app.Constants.TEST_DOMAIN;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    private static boolean isPlaying;
    private static boolean isPausing;
    private static boolean isPreparing = true;
    private List<LocalMusicBean> musicListHistory = new ArrayList<>();
    private LocalMusicBean playingMusic;
    private OnPlayerEventListener listener;
    private MediaPlayer mPlayer = new MediaPlayer();
    private Handler handler = new Handler();
    private String key;
    //inject DataManager Error nullPointException  但是在dictionarypresenter中注入，正常运行。。。
    DataManager mDataManager;

    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataManager = App.getAppComponent().getDataManager();
        mPlayer.setOnCompletionListener(this);
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
            String path = bean.getPath();
            String suffix = path.substring(path.lastIndexOf("."), path.length());
            key = bean.getTitle() + suffix;
            if (!mDataManager.isMusicAlreadyUploadToQN(bean.getTitle())) {
                insertMusicToQiniu(bean);
                insertIntoMusicDB(bean);
            }
            shareToWX(activity, bean);
        } else {
            Logger.d("null");
        }
    }

    private void insertIntoMusicDB(LocalMusicBean bean) {
        Logger.d("insertIntoMusicDB");
        RealmQNMusicBean mRealmMusicBean = new RealmQNMusicBean();
        long id = mDataManager.getMusicDBSizeForId();
        mRealmMusicBean.setId(id);
        mRealmMusicBean.setMusicName(bean.getTitle());
        mRealmMusicBean.setMusicPath(TEST_DOMAIN + key);
        mRealmMusicBean.setArtistName(bean.getArtist());
        mDataManager.insertMusicBean(mRealmMusicBean);
        Logger.d(TEST_DOMAIN + key);
    }

    //musicUrl : http://qiniu.iwhere.com/track/backgroundMusic/backgroundmusic1.mp3
    ///storage/emulated/0/netease/cloudmusic/Music/赵雷 - 成都.mp3
    //http://7xu8tp.com1.z0.glb.clouddn.com/alwaysblue79.png
    private void insertMusicToQiniu(final LocalMusicBean bean){
        getUploadManagerInstance().put(bean.getPath(), key, getUpToken(key),
                new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            if (info.isOK()) {
                                Logger.d("complete");
                            } else {
                                Logger.d("failed");
                            }
                        }
                },
                new UploadOptions(null, null, false,
                        new UpProgressHandler() {
                            @Override
                            public void progress(String key, double percent) {
                                //Logger.d("progress");
                            }
                        },
                        new UpCancellationSignal() {
                            @Override
                            public boolean isCancelled() {
                                return false;
                            }
                        }
                )
        );
    }

    private void shareToWX(Activity activity, LocalMusicBean bean) {
        Logger.d(TEST_DOMAIN + key);
        BaseShareParam param = new ShareParamAudio(bean.getTitle(), bean.getArtist(), TEST_DOMAIN + key);
        ShareParamAudio paramAudio = (ShareParamAudio)param;
        //ShareAudio audio = new ShareAudio(new ShareImage("http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN"), TEST_DOMAIN + key, "哔哩哔哩2016拜年祭");
        ShareAudio audio = new ShareAudio(new ShareImage(BitmapCache.getInstance().decodeSampledBitmapFromResource(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH, 100, 100)),
                TEST_DOMAIN + key,
                "祭");
        paramAudio.setAudio(audio);
        getBiliShare().share(activity, SocializeMedia.WEIXIN_MONMENT, paramAudio, shareListener);
    }

    protected SocializeListeners.ShareListener shareListener = new SocializeListeners.ShareListenerAdapter() {

        @Override
        public void onStart(SocializeMedia type) {
            Logger.d("onstart");
        }

        @Override
        protected void onComplete(SocializeMedia type, int code, Throwable error) {
            Logger.d("oncomplete");
        }
    };

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        pause();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            Logger.d((int)MusicUtils.getPlayService().getCurrentPosition());
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
