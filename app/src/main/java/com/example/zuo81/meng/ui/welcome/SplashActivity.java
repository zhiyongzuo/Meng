package com.example.zuo81.meng.ui.welcome;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseActivity;
import com.example.zuo81.meng.base.contract.splash.Splash;
import com.example.zuo81.meng.component.PlayService;
import com.example.zuo81.meng.presenter.welcome.SplashPresenterImp;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.example.zuo81.meng.utils.BitmapCache;
import com.example.zuo81.meng.utils.MusicUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import okhttp3.ResponseBody;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.example.zuo81.meng.app.Constants.APP_DIRECTORY;
import static com.example.zuo81.meng.app.Constants.SPLASH_PIC_DIRECTORY_NAME;
import static com.example.zuo81.meng.app.Constants.SPLASH;
import static com.example.zuo81.meng.utils.FileUtils.getExternalFileDir;
import static com.example.zuo81.meng.utils.FileUtils.writeInputStreamToDisk;
import static com.example.zuo81.meng.utils.NetworkUtils.checkNetworkConnect;
import static com.example.zuo81.meng.utils.NetworkUtils.getAPNType;
import static java.lang.Thread.sleep;


@RuntimePermissions
public class SplashActivity extends MVPBaseActivity<SplashPresenterImp> implements Splash.View{
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    @BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuthor;

    private PlayServiceConnection mPlayServiceConnection;
    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initEventAndData() {
        if (MusicUtils.getPlayService() == null) {
            startService();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bindService();
            }
        }, 3000);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Logger.addLogAdapter(new AndroidLogAdapter());
        getExternalFileDir(SPLASH_PIC_DIRECTORY_NAME);
        if(checkNetworkConnect(this)) {
            switch(getAPNType(this)) {
                case "WIFI":
                    SplashActivityPermissionsDispatcher.needsPermissionWithCheck(this);
                    break;
                case "4G":
                case "3G":
                case "2G":
                    Logger.d("4g");
                    break;
                default:
                    Logger.d("unknown");
                    break;
            }
        } else {
            Logger.d("no internet");
            showPic();
        }
    }

    @TargetApi(16)
    @Override
    public void showPic() {
        // 尝试用bitmapcache缓存bitmap加载， 失败..不能这么做的原因是每次冷启动时都无法加载图片
        ivWelcomeBg.setImageBitmap(BitmapCache.getInstance().decodeSampledBitmapFromResource(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH, 100, 100));
    }

    @Override
    public void savePic(ResponseBody body) {
        Logger.d("savepic");
        writeInputStreamToDisk(body.byteStream(), APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH);
    }

    @Override
    public void showAuthor(String authorStr) {
        tvWelcomeAuthor.setText(authorStr);
    }

    @Override
    public void jumpToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClass(context, PlayService.class);
        mPlayServiceConnection = new PlayServiceConnection();
        context.bindService(intent, mPlayServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class PlayServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PlayService playService = ((PlayService.PlayBinder)iBinder).getService();
            MusicUtils.setPlayService(playService);
            jumpToMain();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    @TargetApi(16)
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
//        Logger.d("132425");
        presenter.updateWelcomePic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
        Logger.d("onshowRationale");
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionDenied() {
        Logger.d("onPermissionDenied");
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onNeverAskAgain() {
        Logger.d("onNeverAskAgain");
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    protected void onDestroy() {
        if (mPlayServiceConnection != null) {
            unbindService(mPlayServiceConnection);
        }
        super.onDestroy();
    }
}
