package com.example.zuo81.meng.ui.music.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.ui.music.adapter.SearchMusicListAdapter;
import com.example.zuo81.meng.ui.music.view.SearchMusicView;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMusicFragment extends SupportFragment implements SearchMusicView {
    private SearchMusicListAdapter adapter;
    private RecyclerView rvSearchMusic;
    private List<BaiDuMusicSearchBean> list;

    public SearchMusicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        rvSearchMusic = view.findViewById(R.id.rv_music_search_fragment);
        list = new ArrayList<>();
        adapter = new SearchMusicListAdapter(getContext(), list);
        rvSearchMusic.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchMusic.setAdapter(adapter);
        return view;
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
}
