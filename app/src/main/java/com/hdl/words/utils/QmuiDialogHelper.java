package com.hdl.words.utils;

import android.content.Context;
import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Date 2018/10/9 10:12
 * author HDL
 * Mail 229101253@qq.com
 */
public class QmuiDialogHelper {
    private static QMUITipDialog dialog;

    public static void showLoading(Context context, CharSequence msg, int duration) {
        dialogInit();
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        hide(duration);
    }

    public static void showLoading(Context context, int resId, int duration) {
        showLoading(context, context.getResources().getString(resId), duration);
    }

    public static void showLoading(Context context, int resId) {
        showLoading(context, context.getResources().getString(resId), 0);
    }

    public static void showLoading(Context context, CharSequence msg) {
        showLoading(context, msg, 0);
    }

    public static void showSuccess(Context context, CharSequence msg, int duration) {
        dialogInit();
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        hide(duration);
    }

    public static void showSuccess(Context context, int resId, int duration) {
        showSuccess(context, context.getResources().getString(resId), duration);
    }

    public static void showSuccess(Context context, CharSequence msg) {
        showSuccess(context, msg, 0);
    }

    public static void showSuccess(Context context, int resId) {
        showSuccess(context, context.getResources().getString(resId), 0);
    }

    public static void showFail(Context context, CharSequence msg, int duration) {
        dialogInit();
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        hide(duration);
    }

    public static void showFail(Context context, CharSequence msg) {
        showFail(context, msg, 0);
    }

    public static void showFail(Context context, int resId, int duration) {
        showFail(context, context.getResources().getString(resId), duration);
    }

    public static void showFail(Context context, int resId) {
        showFail(context, context.getResources().getString(resId), 0);
    }

    public static void hide() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void hide(int duration) {
        if (duration > 0) {
            new Handler().postDelayed(() -> hide(), duration);
        }
    }

    private static void dialogInit() {
        if (dialog != null) {
            hide();
            dialog = null;
        }
    }
}
