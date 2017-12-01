package com.example.zuo81.meng.ui.main.activity;

import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.Constants;
import com.example.zuo81.meng.base.MVPBaseActivity;
import com.example.zuo81.meng.base.contract.main.MainContract;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.presenter.main.MainPresenter;
import com.example.zuo81.meng.ui.dictionary.DictionaryFragment;
import com.example.zuo81.meng.ui.gallery.GalleryFragment;
import com.example.zuo81.meng.ui.music.MusicMainFragment;
import com.example.zuo81.meng.ui.setting.SettingFragment;
import com.example.zuo81.meng.utils.FileUtils;
import com.example.zuo81.meng.utils.SPUtils;
import com.orhanobut.logger.Logger;

import java.io.File;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.zuo81.meng.app.Constants.DB_PATH;
import static com.example.zuo81.meng.app.Constants.GALLERY_FRAGMENT;
import static com.example.zuo81.meng.app.Constants.MUSIC_FRAGMENT;
import static com.example.zuo81.meng.app.Constants.KEY_REALM_DB_NAME;
import static com.example.zuo81.meng.app.Constants.SEARCH_TYPE_DICTIONARY;
import static com.example.zuo81.meng.utils.FileUtils.restoreFromBackUp;

public class MainActivity extends MVPBaseActivity<MainPresenter>
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private long firstTime = 0;
    private int showFragment;
    private int CURRENT_ITEM_LAYOUT_ID;
    private Class firstLoadClass;

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        if(findFragment(DictionaryFragment.class) == null) {
            loadRootFragment(R.id.fl_main_content, getFirstLoadFragment());
        }

        toolbar.setTitle(navigationView.getMenu().findItem(CURRENT_ITEM_LAYOUT_ID).getTitle().toString());
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getMenu().findItem(CURRENT_ITEM_LAYOUT_ID).setChecked(true);
        navigationView.setCheckedItem(CURRENT_ITEM_LAYOUT_ID);
    }

    private SupportFragment getFirstLoadFragment() {
        int i = SPUtils.getFirstLoadFragmentItemNumber();
        switch(i) {
            case MUSIC_FRAGMENT:
                CURRENT_ITEM_LAYOUT_ID = R.id.nav_music;
                firstLoadClass = MusicMainFragment.class;
                showFragment = Constants.SEARCH_TYPE_MAIN_MUSIC;
            return MusicMainFragment.newInstance();
            case GALLERY_FRAGMENT:
                CURRENT_ITEM_LAYOUT_ID = R.id.nav_gallery;
                firstLoadClass = GalleryFragment.class;
                showFragment = Constants.SEARCH_TYPE_GALLERY;
                return GalleryFragment.newInstance();
        }
        CURRENT_ITEM_LAYOUT_ID = R.id.nav_dictionary;
        firstLoadClass = DictionaryFragment.class;
        showFragment = SEARCH_TYPE_DICTIONARY;
        return DictionaryFragment.newInstance();
    }

    @Override
    public void onBackPressedSupport() {
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
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)item.getActionView();
        if (showFragment == SEARCH_TYPE_DICTIONARY) {
            searchView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if(showFragment == Constants.SEARCH_TYPE_MAIN_MUSIC) {
            searchView.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (showFragment == SEARCH_TYPE_DICTIONARY) {
                    RXBus.getInstance().post(new SearchEvent(query, SEARCH_TYPE_DICTIONARY));
                } else if(showFragment == Constants.SEARCH_TYPE_MAIN_MUSIC) {
                    Logger.d("onQueryTextSubmit");
                    MusicMainFragment fragment = MusicMainFragment.newInstance();
                    fragment.doSearch(query);
                }
                return false;
            }
        });
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();
        if(id == R.id.nav_dictionary) {
            showFragment = SEARCH_TYPE_DICTIONARY;
            DictionaryFragment fragment = findFragment(DictionaryFragment.class);
            if (fragment == null) {
                popTo(firstLoadClass, false, new Runnable() {
                    @Override
                    public void run() {
                        start(DictionaryFragment.newInstance());
                    }
                });
            } else {
                start(fragment, SupportFragment.SINGLETASK);
            }
        }else if(id == R.id.nav_music) {
            showFragment = Constants.SEARCH_TYPE_MAIN_MUSIC;
            MusicMainFragment mMusicFragment = findFragment(MusicMainFragment.class);
            if(mMusicFragment == null) {
                popTo(firstLoadClass, false, new Runnable() {
                    @Override
                    public void run() {
                        start(MusicMainFragment.newInstance());
                    }
                });
            } else {
                start(mMusicFragment, SupportFragment.SINGLETASK);
            }
        }else if (id == R.id.nav_camera) {
//
        } else if (id == R.id.nav_gallery) {
            GalleryFragment mGalleryFragment = findFragment(GalleryFragment.class);
            if(mGalleryFragment == null) {
                //如果targetFragmentClass不是DictionaryClass,则不会跳转fragment
                popTo(firstLoadClass, false, new Runnable() {
                    @Override
                    public void run() {
                        start(GalleryFragment.newInstance());
                    }
                });
            } else {
                start(mGalleryFragment, SupportFragment.SINGLETASK);
            }

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            SettingFragment mSettingFragment = findFragment(SettingFragment.class);
            if(mSettingFragment == null) {
                popTo(firstLoadClass, false, new Runnable() {
                    @Override
                    public void run() {
                        start(SettingFragment.newInstance());
                    }
                });
            }

        } else if (id == R.id.nav_backup) {
            presenter.backupRealmDB();
        } else if (id == R.id.nav_restore) {
            presenter.restoreRealmDB();
        }
        return true;
    }

    @Override
    public void toastSuccess(String string) {
        Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
