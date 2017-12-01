package com.example.zuo81.meng.ui.music;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.GlideApp;
import com.example.zuo81.meng.base.NoMVPBaseFragment;
import com.example.zuo81.meng.component.PlayService;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.service.OnPlayerEventListener;
import com.example.zuo81.meng.ui.music.adapter.MyFragmentPagerAdapter;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;
import com.example.zuo81.meng.ui.music.fragment.SearchMusicFragment;
import com.example.zuo81.meng.utils.MusicUtils;
import com.example.zuo81.meng.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.wcy.lrcview.LrcView;

import static com.example.zuo81.meng.app.Constants.SEARCH_TYPE_LOCALE_MUSIC;
import static com.example.zuo81.meng.app.Constants.SEARCH_TYPE_PLAYED_MUSIC;

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
    @BindView(R.id.iv_play_bar_album_pic)
    ImageView ivAlbumPic;
    @BindView(R.id.tv_play_bar_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_play_bar_song_author)
    TextView tvSongAuthor;
    @BindView(R.id.iv_play_bar_lrc_view)
    LrcView lrcView;
    @BindView(R.id.sb_play_bar)
    SeekBar sbPlayBar;
    @BindView(R.id.iv_play_bar_play)
    ImageView ivPlay;
    @BindView(R.id.iv_play_bar_next)
    ImageView ivNext;
    @BindView(R.id.iv_play_bar_pre)
    ImageView ivPre;
    private List<Fragment> list;
    private LocalMusicBean bean;
    private PlayService playService;

    public static MusicMainFragment newInstance() {
        return new MusicMainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

    @Override
    protected void initEventAndData() {
        playService = MusicUtils.getPlayService();
        playService.setOnPlayerEventListener(this);
        list = new ArrayList<>();
        list.add(new LocalMusicFragment());
        list.add(new SearchMusicFragment());
        mConstraintView.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        ivAlbumPic.setOnClickListener(this);
        lrcView.setVisibility(View.GONE);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        for (int i=0; i<list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("本地");
        mTabLayout.getTabAt(1).setText("在线");

        long id = SPUtils.getCurrentSongId();
        bean = MusicUtils.queryFromId(id);
        if (bean != null) {
            showUI(bean);
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.play_bar:
                break;
            case R.id.iv_play_bar_album_pic:
                playService.share(getActivity());
                break;
            case R.id.iv_play_bar_play:
                playService.buttonPlayClick(getActivity());
                break;
            case R.id.iv_play_bar_pre:
                break;
            case R.id.iv_play_bar_next:
                break;
            default:
                break;
        }
    }

    @Override
    public void showUI(LocalMusicBean bean) {
        if(bean == null) {
            return;
        }
        sbPlayBar.setMax((int)bean.getDuration());
        sbPlayBar.setProgress(0);
        sbPlayBar.setPadding(0, 0, 0, 0);
        GlideApp.with(context).load(bean.getCoverPath()).into(ivAlbumPic);
        tvSongName.setText(bean.getTitle());
        if (!lrcView.hasLrc()) {
            tvSongAuthor.setVisibility(View.VISIBLE);
            lrcView.setVisibility(View.GONE);
            tvSongAuthor.setText(bean.getArtist() + " - " + bean.getAlbum());
        } else {
            //有歌词并且非播放中
            tvSongAuthor.setVisibility(View.GONE);
            lrcView.setVisibility(View.VISIBLE);
            //lrcView.loadLrc(bean.)
        }
    }

    @Override
    public void showSeekBarProgress(int progress) {
        sbPlayBar.setProgress(progress);
    }

    public void doSearch(String query) {
        switch(mViewPager.getCurrentItem()) {
            case SEARCH_TYPE_LOCALE_MUSIC:
                RXBus.getInstance().post(new SearchEvent(query, SEARCH_TYPE_LOCALE_MUSIC));
                break;
            case SEARCH_TYPE_PLAYED_MUSIC:
                Logger.d("doSearch");
                RXBus.getInstance().post(new SearchEvent(query, SEARCH_TYPE_PLAYED_MUSIC));
                break;
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
