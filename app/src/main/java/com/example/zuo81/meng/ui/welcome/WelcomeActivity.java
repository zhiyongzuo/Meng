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

import static com.example.zuo81.meng.app.Constants.PATH_SDCARD;
import static com.example.zuo81.meng.app.Constants.SPLASH;
import static com.example.zuo81.meng.utils.FileUtils.getExternalFileDir;
import static com.example.zuo81.meng.utils.FileUtils.writeInputStreamToDisk;
import static com.example.zuo81.meng.utils.SystemUtil.isWifiConnected;


@RuntimePermissions
public class WelcomeActivity extends MVPBaseActivity<WelcomePresenterImp> implements Welcome.View{
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
        getExternalFileDir(PATH_SDCARD);
        if (isWifiConnected()) {
            WelcomeActivityPermissionsDispatcher.needsPermissionWithCheck(this);
        } else {
            showPic();
        }
    }

    @TargetApi(16)
    @Override
    public void showPic() {
        File file = new File(PATH_SDCARD, SPLASH);
        if(file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            //ivWelcomeBg.setImageBitmap(bitmap);
            ivWelcomeBg.setBackground(new BitmapDrawable(bitmap));
        }
        jumpToMain();
    }

    @Override
    public void savePic(ResponseBody body) {
        writeInputStreamToDisk(body.byteStream(), PATH_SDCARD, SPLASH);
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
        presenter.updateWelcomePic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
