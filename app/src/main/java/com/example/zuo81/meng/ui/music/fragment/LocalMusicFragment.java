package com.example.zuo81.meng.ui.music.fragment;

import android.support.v7.widget.RecyclerView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.NoMVPBaseFragment;
import com.example.zuo81.meng.base.contract.music.LocalMusic;
import com.example.zuo81.meng.base.contract.music.SearchMusic;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.presenter.music.LocalMusicPresenter;
import com.example.zuo81.meng.ui.music.adapter.LocalMusicListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class LocalMusicFragment extends MVPBaseFragment<LocalMusicPresenter> implements LocalMusic.View {
    @BindView(R.id.rv_fragment_local_music)
    RecyclerView rvLocalMusic;

    private LocalMusicListAdapter adapter;
    private List<LocalMusicBean> mList = new ArrayList<>();

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
        adapter = new LocalMusicListAdapter(mList, getContext());
    }

    @Override
    public void showContent(List<BaiDuMusicSearchBean> baiDuBeanList) {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
