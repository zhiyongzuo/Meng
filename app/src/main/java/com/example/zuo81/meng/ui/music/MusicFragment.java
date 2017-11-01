package com.example.zuo81.meng.ui.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.ui.music.adapter.MyFragmentPagerAdapter;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class MusicFragment extends SupportFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> list;

    public static MusicFragment newInstance() {
        return new MusicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.add(new LocalMusicFragment());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mTabLayout = view.findViewById(R.id.tablayout_fragment_music);
        mViewPager = view.findViewById(R.id.viewpager_fragment_music);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        for (int i=0; i<list.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab());
        }
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("0");
        mTabLayout.getTabAt(1).setText("1");
        mTabLayout.getTabAt(2).setText("2");
        return view;
    }
}
