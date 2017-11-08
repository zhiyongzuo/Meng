package com.example.zuo81.meng.ui.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.ui.music.adapter.MyFragmentPagerAdapter;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;
import com.example.zuo81.meng.ui.music.fragment.SearchMusicFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class MusicMainFragment extends SupportFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ConstraintLayout mConstraintView;
    private ImageView ivPlay;
    private ImageView ivNext;
    private List<Fragment> list;

    public static MusicMainFragment newInstance() {
        return new MusicMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        list.add(new LocalMusicFragment());
        list.add(new SearchMusicFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mTabLayout = view.findViewById(R.id.tablayout_fragment_music);
        mViewPager = view.findViewById(R.id.viewpager_fragment_music);
        mConstraintView = (ConstraintLayout) view.findViewById(R.id.play_bar);
        ivPlay = (ImageView) view.findViewById(R.id.iv_play_bar_play);
        ivNext = (ImageView) view.findViewById(R.id.iv_play_bar_next);

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
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.play_bar:
                break;
            case R.id.iv_play_bar_play:
                break;
            case R.id.iv_play_bar_next:
                break;
            default:
                break;
        }
    }
}
