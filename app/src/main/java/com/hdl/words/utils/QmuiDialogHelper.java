package com.hdl.words.utils;

import android.content.Context;
import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Date 2018/10/9 10:12
 * author HDL
 * Mail 229101253@qq.com
 */
public  class QmuiDialogHelper {
    private static QMUITipDialog dialog;
    public static void showLoading(Context context, CharSequence msg){
        dialogInit();
        dialog=new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailSuccess(Context context, CharSequence msg){
        dialogInit();
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailSuccess(Context context, int resId){
        showMailSuccess(context,context.getResources().getString(resId));
    }
    public static void showMailFail(Context context, CharSequence msg){
        dialogInit();
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailFail(Context context, int resId){
        showMailFail(context,context.getResources().getString(resId));
    }
    public static void hide(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }
    public static void hide(int duration){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hide();
            }
        },duration);
    }
    private static void dialogInit(){
        if(dialog!=null){
            hide();
            dialog=null;
        }
    }
}
