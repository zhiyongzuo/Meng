package com.example.zuo81.meng.ui.main.activity;

import android.os.Bundle;
import android.os.Environment;
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
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.ui.dictionary.DictionaryFragment;
import com.example.zuo81.meng.ui.gallery.GalleryFragment;
import com.example.zuo81.meng.ui.main.view.MainView;
import com.example.zuo81.meng.ui.music.MusicMainFragment;
import com.orhanobut.logger.Logger;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.UUID;

import io.realm.Realm;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.zuo81.meng.app.Constants.BUCKET_NAME;
import static com.example.zuo81.meng.app.Constants.TEST_DOMAIN;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

public class MainActivity extends SupportActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView{

    private Toolbar toolbar;
    private long firstTime = 0;
    private int showFragment = Constants.TYPE_DICTIONARY;
    //qiniu
    String key = "default.realm";
    String dbPath = Realm.getDefaultInstance().getPath();
    //
    public InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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
//
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "nav_gallery", Toast.LENGTH_SHORT).show();
            GalleryFragment mGalleryFragment = findFragment(GalleryFragment.class);
            if(mGalleryFragment == null) {
                //如果targetFragmentClass不是DictionaryClass,则不会跳转fragment
                popTo(DictionaryFragment.class, false, new Runnable() {
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

        } else if (id == R.id.nav_backup) {
            String backUpPath = Environment.getExternalStorageDirectory() + "/Meng/BackUp";
            File backUpathFile = new File(backUpPath);
            if(!backUpathFile.exists()) {
                backUpathFile.mkdirs();
            }
            File backUpFile = new File(backUpathFile, key);
            if(backUpFile.exists()) {
                backUpFile.delete();
            }
            Realm realm = Realm.getDefaultInstance();
            realm.writeCopyTo(backUpFile);

            UploadOptions upOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
                @Override
                public void progress(String s, double v) {
                    Logger.d(s + " / " + v);
                }
            }, new UpCancellationSignal() {
                @Override
                public boolean isCancelled() {
                    return false;
                }
            });

            getUploadManagerInstance().put(backUpFile, key, getUpToken(key), new UpCompletionHandler() {
                @Override
                public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                    if(responseInfo.isOK()) {
                        Logger.d("ok");
                        Toast.makeText(MainActivity.this, "backup success", Toast.LENGTH_SHORT).show();
                    } else {
                        Logger.d("failure");
                        Logger.d(responseInfo.error);
                    }
                }
            }, upOptions);

        } else if (id == R.id.nav_restore) {
            try {
                FileChannel inChannel = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/Meng/BackUp", key)).getChannel();
                FileChannel outChannel = new FileOutputStream(new File(dbPath)).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*final String restore_url = TEST_DOMAIN + key + "?v=" + UUID.randomUUID().getLeastSignificantBits();
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isClosed()) {
                realm.close();
            }
            String restorePath = Environment.getExternalStorageDirectory() + "/Meng/Restore";
            final File restorePathFile = new File(restorePath);
            if(!restorePathFile.exists()) {
                restorePathFile.mkdirs();
            }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File restoreFile = new File(restorePathFile, key);
                            if(restoreFile.exists()) {
                                restoreFile.delete();
                            }
                            URL url = new URL(restore_url);
                            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
                            is = urlConn.getInputStream();
                            Logger.d("xxxx");
                            OutputStream os = new FileOutputStream(restoreFile);
                            byte[] b = new byte[1024];
                            while(is.read(b) != -1) {
                                os.write(b);
                            }
                            os.flush();
                            is.close();
                            os.close();

                            FileChannel inChannel = new FileInputStream(restoreFile).getChannel();
                            FileChannel outChannel = new FileOutputStream(new File(dbPath)).getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                            inChannel.close();
                            outChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Toast.makeText(MainActivity.this, "restore success", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
