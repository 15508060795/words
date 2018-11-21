package com.hdl.words.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Date 2018/10/9 12:13
 * auther HDL
 * Mail 229101253@qq.com
 */
public class ToastHelper {
    private static Toast toast;
    /**
     * [简化Toast]
     * @param msg
     */
    public static void shortToast(Context context, String msg){
        if(toast==null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * [简化Toast]
     * @param msg
     */
    public static void longToast(Context context, String msg){
        if(toast==null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
    public static void toastTime(Context context, String msg, int duration){
        if(toast==null){
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else{
            toast.setText(msg);
        }
        toast.setDuration(duration);
        toast.show();
    }
    public static void hide(Context context){
        if(toast!=null){
            toast.cancel();
        }
    }
}
