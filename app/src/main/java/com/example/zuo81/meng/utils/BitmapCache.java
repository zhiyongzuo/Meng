package com.example.zuo81.meng.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.zuo81.meng.app.Constants.APP_DIRECTORY;
import static com.example.zuo81.meng.app.Constants.MAP;
import static com.example.zuo81.meng.app.Constants.SPLASH;
import static com.example.zuo81.meng.app.Constants.SPLASH_PIC_DIRECTORY_NAME;

/**
 * Created by zuo81 on 2017/11/28.
 */

public class BitmapCache {
    private HashMap<String, Bitmap> map = new HashMap<>();

    private BitmapCache() {
    }

    public static BitmapCache getInstance() {
        return Holder.cache;
    }

    public static class Holder {
        static BitmapCache cache = new BitmapCache();
    }

    public void put(Bitmap bitmap) {
        map.clear();
        map.put(MAP, bitmap);
    }

    public Bitmap get() {
        return map.get(MAP);
    }

    public void saveBitmapCacheFromFile(String path, String name) {
        if(FileUtils.isFileExists(path, name)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path + File.separator + name);
            if (bitmap != null) {
                put(bitmap);
            } else {
                Logger.d("bitmap in null");
            }
        } else {
            Logger.d("pic file not exists");
        }
    }

    public Bitmap decodeSampledBitmapFromResource(String path, String name, int reqWidth, int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //加载图片
        BitmapFactory.decodeFile(path + File.separator + name, options);
        //计算缩放比
        options.inSampleSize = calculateInSampleSize(options,reqHeight,reqWidth);
        //重新加载图片
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeFile(path + File.separator + name, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight||width>reqWidth){
            int halfHeight = height/2;
            int halfWidth = width/2;
            //计算缩放比，是2的指数
            while((halfHeight/inSampleSize)>=reqHeight&&(halfWidth/inSampleSize)>=reqWidth){
                inSampleSize*=2;
            }
        }
        return inSampleSize;
    }
}
