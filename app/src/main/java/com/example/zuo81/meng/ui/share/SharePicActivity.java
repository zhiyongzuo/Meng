package com.example.zuo81.meng.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseActivity;
import com.example.zuo81.meng.base.contract.share.Share;
import com.example.zuo81.meng.di.Component.ActivityComponent;
import com.example.zuo81.meng.presenter.share.SharePresenter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;

import static android.content.Intent.EXTRA_STREAM;

public class SharePicActivity extends MVPBaseActivity<SharePresenter> implements Share.View {
    @BindView(R.id.activity_share_number_progress_bar)
    NumberProgressBar numberProgressBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share_pic;
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void initEventAndData() {
        Intent intent = getIntent();
        if(intent!=null && intent.getExtras()!=null && intent.getExtras().containsKey(EXTRA_STREAM)) {
            Uri uri = (Uri)intent.getExtras().getParcelable(EXTRA_STREAM);
            if (uri != null) {
                Logger.d(uri.getPath());//  /storage/emulated/0/Android/data/com.null
                presenter.backupPic(uri.getPath());
            }
        }
    }

    public void showProgress(int progress) {
        numberProgressBar.setProgress(progress);
    }

    @Override
    public void hideProgress() {
        numberProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void uploadSuccess(long number) {
        Toast.makeText(this, number + " upload success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}
