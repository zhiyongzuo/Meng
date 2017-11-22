package com.example.zuo81.meng.base;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

public abstract class NoMVPBaseActivity extends SupportActivity {
    private Unbinder unbinder;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = this;
        unbinder = ButterKnife.bind(this);
        App.getInstance().addActivity(this);
        initEventAndData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        unbinder.unbind();
    }

    protected abstract int getLayoutId();
    protected abstract void initEventAndData();

}
