package com.example.zuo81.meng.component;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zuo81 on 2017/10/24.
 */

public class ImageLoader {

    public static void load(Activity activity, String url, ImageView iv) {
        Glide.with(activity).load(url).into(iv);
    }
}
