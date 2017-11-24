package com.example.zuo81.meng.ui.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.NoMVPBaseFragment;
import com.example.zuo81.meng.component.PlayService;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.ui.music.adapter.MyFragmentPagerAdapter;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;
import com.example.zuo81.meng.ui.music.fragment.SearchMusicFragment;
import com.example.zuo81.meng.utils.MusicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class MusicMainFragment extends NoMVPBaseFragment implements View.OnClickListener, OnPlayerEventListener {
    @BindView(R.id.tablayout_fragment_music)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager_fragment_music)
    ViewPager mViewPager;
    @BindView(R.id.play_bar)
    ConstraintLayout mConstraintView;
    @BindView(R.id.iv_play_bar_play)
    ImageView ivPlay;
    @BindView(R.id.iv_play_bar_next)
    ImageView ivNext;
    @BindView(R.id.iv_play_bar_pre)
    ImageView ivPre;
    private List<Fragment> list;
    private PlayServiceConnection mPlayServiceConnection;

    private Handler handler = new Handler();

    public static MusicMainFragment newInstance() {
        return new MusicMainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initEventAndData() {
        startService();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bindService();
            }
        }, 1000);
        MusicUtils.getPlayService().setOnPlayerEventListener(this);
        list = new ArrayList<>();
        list.add(new LocalMusicFragment());
        list.add(new SearchMusicFragment());
        mConstraintView.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        for (int i=0; i<list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("本地");
        mTabLayout.getTabAt(1).setText("在线");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.play_bar:
                break;
            case R.id.iv_play_bar_play:
                    if() {
                        MusicUtils.getPlayService().play();
                    } else {
                        MusicUtils.getPlayService().pause();
                    }
                break;
            case R.id.iv_play_bar_pre:
                break;
            case R.id.iv_play_bar_next:
                break;
            default:
                break;
        }
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), PlayService.class);
        activity.startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(context, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        context.bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService playService = ((PlayService.PlayBinder)iBinder).getService();
            MusicUtils.setPlayService(playService);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }


    @Override
    public void status_play() {
        ivPlay.setSelected(true);
    }

    @Override
    public void status_pause() {
        ivPlay.setSelected(false);
    }
}
