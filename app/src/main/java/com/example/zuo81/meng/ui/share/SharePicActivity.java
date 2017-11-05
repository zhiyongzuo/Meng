package com.example.zuo81.meng.ui.share;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zuo81.meng.R;
import com.orhanobut.logger.Logger;

public class SharePicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_pic);
        Intent intent = getIntent();
        if(intent == null) {
            Logger.d("intent is null");
        } else {
            Bundle bundle = intent.getExtras();
            if(bundle == null) {
                Logger.d("bundle is null");
            }
            switch(intent.getType()) {
                case "text/plain":
                    Logger.d(bundle.get(Intent.EXTRA_TITLE) + "   " + bundle.get(Intent.EXTRA_TEXT));
                    break;
                case "image/*":
                    Logger.d("image/*");
                    Logger.d(bundle.get(Intent.EXTRA_TITLE) + "   " + bundle.get(Intent.EXTRA_TEXT));
                    break;
                default:
                    break;
            }
        }
    }
}
