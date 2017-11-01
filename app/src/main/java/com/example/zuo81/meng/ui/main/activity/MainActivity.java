package com.example.zuo81.meng.ui.main.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.App;
import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.ui.dictionary.DictionaryFragment;
import com.example.zuo81.meng.ui.main.view.MainView;
import com.example.zuo81.meng.ui.music.MusicFragment;
import com.example.zuo81.meng.widget.DefaultItemTouchHelpCallback;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private Toolbar toolbar;
    private long firstTime = 0;
    private DictionaryFragment mDictionaryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDictionaryFragment = new DictionaryFragment();
        if(findFragment(DictionaryFragment.class) == null) {
            loadRootFragment(R.id.fl_main_content, mDictionaryFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(R.id.nav_dictionary).setChecked(true);

        toolbar.setTitle(navigationView.getMenu().findItem(R.id.nav_dictionary).getTitle().toString());

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                RXBus.getInstance().post(new SearchEvent(query, Constants.TYPE_DICTIONARY));
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_dictionary) {
            start(mDictionaryFragment, SupportFragment.SINGLETASK);
        }else if(id == R.id.nav_music) {
            MusicFragment mMusicFragment = findFragment(MusicFragment.class);
            if(mMusicFragment == null) {
                popTo(DictionaryFragment.class, false, new Runnable() {
                    @Override
                    public void run() {
                        start(MusicFragment.newInstance());
                    }
                });
            }
        }else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        toolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
