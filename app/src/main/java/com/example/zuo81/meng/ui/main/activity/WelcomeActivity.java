package com.example.zuo81.meng.ui.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.BaseActivity;
import com.example.zuo81.meng.base.contract.main.WelcomeContract;
import com.example.zuo81.meng.component.ImageLoader;
import com.example.zuo81.meng.model.WelcomeBean;

import butterknife.BindView;


public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {
    @BindView(R.id.iv_welcome_bg)
    ImageView ivWelcomeBg;
    @BindView(R.id.tv_welcome_author)
    TextView tvWelcomeAuther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initInject() {
        super.initInject();
    }

    @Override
    public void showContent(WelcomeBean welcomeBean) {
        ImageLoader.load(this, welcomeBean.getImg(), ivWelcomeBg);
    }

    @Override
    public void jumpToMain() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
