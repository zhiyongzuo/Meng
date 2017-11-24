package com.example.zuo81.meng.ui.music.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.music.LocalMusic;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.presenter.music.LocalMusicPresenter;
import com.example.zuo81.meng.ui.music.adapter.LocalMusicListAdapter;
import com.example.zuo81.meng.ui.music.adapter.OnlineMusicListAdapter;
import com.example.zuo81.meng.utils.MusicUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class LocalMusicFragment extends MVPBaseFragment<LocalMusicPresenter> implements LocalMusic.View {
    @BindView(R.id.rv_fragment_local_music)
    RecyclerView rv;
    @BindView(R.id.number_progress_bar_fragment_local_music)
    NumberProgressBar numberProgressBar;
    @BindView(R.id.progress_bar_fragment_local_music)
    ProgressBar progressBar;

    private LocalMusicListAdapter adapter;

    public String getTitle() {
        return "11";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_local_music;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        showProgress();
        presenter.loadMusicList();
        rv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LocalMusicListAdapter(getContext());
        rv.setAdapter(adapter);
        adapter.setOnDeleteClickListener(new LocalMusicListAdapter.OnDeleteClickListener() {
            @Override
            public void click() {

            }
        });
    }


    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContent(List<LocalMusicBean> baiDuBeanList) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
