package com.example.zuo81.meng.ui.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.WelcomeBean;
import com.example.zuo81.meng.presenter.welcome.WelcomePresenter;
import com.example.zuo81.meng.presenter.welcome.WelcomePresenterImp;
import com.example.zuo81.meng.ui.main.activity.MainActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;



public class WelcomeActivity extends AppCompatActivity implements WelcomeView {
    private ImageView ivWelcomeBg;
    private TextView tvWelcomeAuthor;
    private WelcomePresenter mWelcomePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ivWelcomeBg = findViewById(R.id.iv_welcome_bg);
        tvWelcomeAuthor = findViewById(R.id.tv_welcome_author);
        Logger.addLogAdapter(new AndroidLogAdapter());
        mWelcomePresenter = new WelcomePresenterImp(this);
        mWelcomePresenter.loadWelcomePic();
    }

    @Override
    public void showPic(WelcomeBean welcomeBean) {
        String url = "http://www.bing.com/" + welcomeBean.getImages().get(0).getUrl();
        //Glide.with(this).load(url).into(ivWelcomeBg);
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

    @Override
    protected void onDestroy() {
        mWelcomePresenter.onDestroy();
        super.onDestroy();
    }
}
