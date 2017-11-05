package com.example.zuo81.meng.ui.main.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.ui.dictionary.DictionaryFragment;
import com.example.zuo81.meng.ui.main.view.MainView;
import com.example.zuo81.meng.ui.music.MusicMainFragment;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, View.OnClickListener {

    private Toolbar toolbar;
    private ConstraintLayout mConstraintView;
    private ImageView ivPlay;
    private ImageView ivNext;
    private long firstTime = 0;
    private int showFragment = Constants.TYPE_DICTIONARY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mConstraintView = (ConstraintLayout) findViewById(R.id.play_bar);
        ivPlay = (ImageView) findViewById(R.id.iv_play_bar_play);
        ivNext = (ImageView) findViewById(R.id.iv_play_bar_next);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        mConstraintView.setOnClickListener(this);
        ivPlay.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        toolbar.setTitle("dictinoary");
        setSupportActionBar(toolbar);

        if(findFragment(DictionaryFragment.class) == null) {
            loadRootFragment(R.id.fl_main_content, DictionaryFragment.newInstance());
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_dictionary).setChecked(true);
    }

    @Override
    public void onBackPressedSupport() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();
            if(secondTime - firstTime < 2000) {
                finish();
            } else {
                Toast.makeText(this, R.string.click_second_time_for_exit, Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();
        if (showFragment == Constants.TYPE_DICTIONARY) {
            searchView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if(showFragment == Constants.TYPE_SEARCH_MUSIC) {
            searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (showFragment == Constants.TYPE_DICTIONARY) {
                    RXBus.getInstance().post(new SearchEvent(query, Constants.TYPE_DICTIONARY));
                } else if(showFragment == Constants.TYPE_SEARCH_MUSIC) {
                    //searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                    RXBus.getInstance().post(new SearchEvent(query, Constants.TYPE_SEARCH_MUSIC));
                }
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if(id == R.id.nav_dictionary) {
            Logger.d("dictionary");
            showFragment = Constants.TYPE_DICTIONARY;
            DictionaryFragment fragment = findFragment(DictionaryFragment.class);
            if (fragment == null) {
            } else {
                Logger.d("not null");
                //start(fragment, SupportFragment.SINGLETASK);
                popTo(DictionaryFragment.class, false);
            }
        }else if(id == R.id.nav_music) {
            Toast.makeText(this, "nav_music", Toast.LENGTH_SHORT).show();
            showFragment = Constants.TYPE_SEARCH_MUSIC;
            MusicMainFragment mMusicFragment = findFragment(MusicMainFragment.class);
            if(mMusicFragment == null) {
                popTo(DictionaryFragment.class, false, new Runnable() {
                    @Override
                    public void run() {
                        start(MusicMainFragment.newInstance());
                    }
                });
            } else {
                start(mMusicFragment, SupportFragment.SINGLETASK);
            }
        }else if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        return true;
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
