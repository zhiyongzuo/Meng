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
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.NoMVPBaseFragment;
import com.example.zuo81.meng.ui.music.adapter.MyFragmentPagerAdapter;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;
import com.example.zuo81.meng.ui.music.fragment.SearchMusicFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/11/1.
 */

public class MusicMainFragment extends NoMVPBaseFragment implements View.OnClickListener {
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
    private List<Fragment> list;

    public static MusicMainFragment newInstance() {
        return new MusicMainFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_music;
    }

   /* @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }
*/
    @Override
    protected void initEventAndData() {
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
                break;
            case R.id.iv_play_bar_next:
                break;
            default:
                break;
        }
    }

}
