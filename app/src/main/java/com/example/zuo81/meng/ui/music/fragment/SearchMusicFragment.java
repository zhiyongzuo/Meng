package com.example.zuo81.meng.ui.music.fragment;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.music.SearchMusic;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.presenter.music.SearchMusicPresenter;
import com.example.zuo81.meng.ui.music.adapter.OnlineMusicListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMusicFragment extends MVPBaseFragment<SearchMusicPresenter> implements SearchMusic.View{
    @BindView(R.id.rv_music_search_fragment)
    RecyclerView rvSearchMusic;

    private OnlineMusicListAdapter adapter;
    private List<BaiDuMusicSearchBean> list = new ArrayList<>() ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_music;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        adapter = new OnlineMusicListAdapter(getContext(), list);
        adapter.setOnDeleteClickListener(new OnlineMusicListAdapter.OnDetailClickListener() {
            @Override
            public void click() {

            }
        });
        rvSearchMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchMusic.setAdapter(adapter);
    }

    @Override
    public void showContent(List<BaiDuMusicSearchBean> baiDuBeanList) {
        list.clear();
        list.addAll(baiDuBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProcess() {

    }

    @Override
    public void hideProcess() {

    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
