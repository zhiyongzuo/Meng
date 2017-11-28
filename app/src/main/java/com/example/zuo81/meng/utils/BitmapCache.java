package com.example.zuo81.meng.utils;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zuo81.meng.app.Constants.MAP;

/**
 * Created by zuo81 on 2017/11/28.
 */

public class BitmapCache {
    private Map<String, Bitmap> map = new HashMap<>();

    private BitmapCache() {
    }

    public static BitmapCache getInstance() {
        return Holder.cache;
    }

    public static class Holder {
        static BitmapCache cache = new BitmapCache();
    }

    public void put(Bitmap bitmap) {
        map.put(MAP, bitmap);
    }

    public Bitmap get() {
        return map.get(MAP);
    }
}
