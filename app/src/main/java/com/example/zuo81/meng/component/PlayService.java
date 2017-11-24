package com.example.zuo81.meng.component;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.utils.Preferences;

import java.io.IOException;

public class PlayService extends Service implements MediaPlayer.OnCompletionListener {
    private OnPlayerEventListener listener;
    private MediaPlayer mPlayer = new MediaPlayer();

    public PlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void setOnPlayerEventListener(OnPlayerEventListener listener) {
        this.listener = listener;
    }
    public void play(LocalMusicBean bean) {
        listener.status_play();
        Preferences.saveCurrentSongId(bean.getId());
        try {
            mPlayer.reset();
            mPlayer.setDataSource(bean.getPath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        listener.status_pause();
        mPlayer.pause();
    }

    public void prePlay() {

    }

    public void nextPlay() {

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
