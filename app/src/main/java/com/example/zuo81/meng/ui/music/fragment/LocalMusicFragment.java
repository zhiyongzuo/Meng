package com.example.zuo81.meng.ui.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.ui.music.adapter.LocalMusicListAdapter;
import com.example.zuo81.meng.ui.music.adapter.SearchMusicListAdapter;
import com.example.zuo81.meng.ui.music.view.MusicView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class LocalMusicFragment extends SupportFragment implements MusicView {
    private RecyclerView rvLocalMusic;
    private LocalMusicListAdapter adapter;
    private List<LocalMusicBean> mList;

    public String getTitle() {
        return "11";
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        rvLocalMusic = view.findViewById(R.id.rv_fragment_local_music);
        adapter = new LocalMusicListAdapter(mList, getContext());
        return view;
    }

    @Override
    public void showContent(List<BaiDuMusicSearchBean> baiDuBeanList) {

    }
}
