package com.example.zuo81.meng.ui.welcome;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseActivity;
import com.example.zuo81.meng.base.contract.welcome.Welcome;
import com.example.zuo81.meng.presenter.welcome.WelcomePresenterImp;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.example.zuo81.meng.utils.BitmapCache;
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


@RuntimePermissions
public class SplashActivity extends MVPBaseActivity<WelcomePresenterImp> implements Welcome.View{
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    @BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuthor;

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
        Logger.d("1");
        Bitmap bitmap = BitmapCache.getInstance().get();
        if(bitmap!=null) {
            ivWelcomeBg.setBackground(new BitmapDrawable(BitmapCache.getInstance().get()));
        }
        jumpToMain();
    }

    @Override
    public void savePic(ResponseBody body) {
        Logger.d("savepic");
        writeInputStreamToDisk(body.byteStream(), APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH);
        File file = new File(APP_DIRECTORY + SPLASH_PIC_DIRECTORY_NAME, SPLASH);
        if(file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            BitmapCache.getInstance().put(bitmap);
        }
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

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needsPermission() {
        Logger.d("132425");
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
}
