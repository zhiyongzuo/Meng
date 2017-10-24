package com.example.zuo81.meng.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by zuo81 on 2017/10/23.
 */

public class SnackbarUtil {

    public static void showLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    public static void showShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}
