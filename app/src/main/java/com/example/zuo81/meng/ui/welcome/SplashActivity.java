package com.example.zuo81.meng.ui.welcome;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseActivity;
import com.example.zuo81.meng.base.contract.welcome.Welcome;
import com.example.zuo81.meng.component.PlayService;
import com.example.zuo81.meng.presenter.welcome.WelcomePresenterImp;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.example.zuo81.meng.ui.music.MusicMainFragment;
import com.example.zuo81.meng.utils.BitmapCache;
import com.example.zuo81.meng.utils.FileUtils;
import com.example.zuo81.meng.utils.MusicUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.File;

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
import static com.example.zuo81.meng.utils.SystemUtil.isWifiConnected;
import static java.lang.Thread.sleep;


@RuntimePermissions
public class SplashActivity extends MVPBaseActivity<WelcomePresenterImp> implements Welcome.View{
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    @BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuthor;

    private PlayServiceConnection mPlayServiceConnection;

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
        //hide virtual button
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Logger.addLogAdapter(new AndroidLogAdapter());
        getExternalFileDir(SPLASH_PIC_DIRECTORY_NAME);
        if (isWifiConnected()) {
            SplashActivityPermissionsDispatcher.needsPermissionWithCheck(this);
        } else {
            showPic();
        }
    }

    @TargetApi(16)
    @Override
    public void showPic() {
        // 尝试用bitmapcache缓存bitmap加载， 失败..不能这么做的原因是每次冷启动时都无法加载图片
        ivWelcomeBg.setImageBitmap(BitmapCache.getInstance().decodeSampledBitmapFromResource(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH, 100, 100));
        if (MusicUtils.getPlayService() == null) {
            startService();
            bindService();
        } else {
            jumpToMain();
        }
    }

    @Override
    public void savePic(ResponseBody body) {
        Logger.d("savepic");
        writeInputStreamToDisk(body.byteStream(), APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH);
        BitmapCache.getInstance().saveBitmapCacheFromFile(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH);
        jumpToMain();
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
