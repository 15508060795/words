package com.hdl.words.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Date 2018/10/9 12:13
 * author HDL
 * Mail 229101253@qq.com
 */
public class ToastHelper {
    private static Toast toast;

    /**
     * [简化Toast]
     *
     * @param msg
     */
    @SuppressLint("ShowToast")
    public static void shortToast(Context context, CharSequence msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void shortToast(Context context, int resId) {
        shortToast(context, context.getResources().getString(resId));
    }

    /**
     * [简化Toast]
     *
     * @param msg
     */
    @SuppressLint("ShowToast")
    public static void longToast(Context context, CharSequence msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void longToast(Context context, int resId) {
        longToast(context, context.getResources().getString(resId));
    }

    @SuppressLint("ShowToast")
    public static void toastTime(Context context, CharSequence msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.setDuration(duration);
        toast.show();
    }

    public static void toastTime(Context context, int resId, int duration) {
        toastTime(context, context.getResources().getString(resId), duration);
    }

    public static void hide(Context context) {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void hide(long time) {
        if (toast != null) {
            new Handler().postDelayed(() -> toast.cancel(), time);
        }
    }
}
