package com.hdl.words.utils;

import android.content.Context;
import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Date 2018/10/9 10:12
 * auther HDL
 * Mail 229101253@qq.com
 */
public  class QmuiDialogHelper {
    private static QMUITipDialog dialog;
    public static void showLoading(Context context, String msg){
        if(dialog!=null){
            hideLoading();
            dialog=null;
        }else{

        }
        dialog=new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailSuccess(Context context, String msg){
        if(dialog!=null){
            hideLoading();
            dialog=null;
        }else {

        }
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailSuccess(Context context, int resId){
        if(dialog!=null){
            hideLoading();
            dialog=null;
        }else {

        }
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(context.getResources().getString(resId))
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailFail(Context context, String msg){
        if(dialog!=null){
            hideLoading();
            dialog=null;
        }else {

        }
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void showMailFail(Context context, int resId){
        if(dialog!=null){
            hideLoading();
            dialog=null;
        }else {

        }
        dialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(context.getResources().getString(resId))
                .create();
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void hideLoading(){
        if(dialog!=null){
            dialog.dismiss();
        }

    }
    public static void hideLoading(long time){
        if(dialog!=null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            },time);
        }

    }
}
