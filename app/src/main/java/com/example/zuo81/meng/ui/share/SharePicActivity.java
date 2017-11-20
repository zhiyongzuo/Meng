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
                presenter.uploadToQinyun(uri.getPath());
                presenter.inserIntoPicUrlDB();
            }

            Bundle bundle = intent.getExtras();
            switch(intent.getType()) {
                case "text/plain":
                    Logger.d(bundle.get(Intent.EXTRA_TITLE) + "   " + bundle.get(Intent.EXTRA_TEXT));
                    String s = (String)bundle.get(Intent.EXTRA_TEXT);
                    //tvShareUrl.setText(s);
                    break;
                case "image/*":
                    //Toast.makeText(this, "pic success", Toast.LENGTH_SHORT).show();
                    Logger.d("image/*");
                    Uri u = (Uri)bundle.get(Intent.EXTRA_STREAM);
                    Logger.d(u.getPath() + "   " + bundle.get(Intent.EXTRA_TEXT));
                    break;
                default:
                    break;
            }
        }
    }

    public void showProgress(int progress) {
        numberProgressBar.setProgress(progress);
    }

    @Override
    public void hideProgress(long number) {
        Toast.makeText(this, number + " upload success", Toast.LENGTH_SHORT).show();
        numberProgressBar.setVisibility(View.GONE);
        finish();
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}