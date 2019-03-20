package com.hdl.words.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MySession {
    private static SharedPreferences sp;
    private static final String sp_name = "sp_demo";
    private static SharedPreferences.Editor editor;
    protected static final String TAG = "SharedPreferences";

    //
    public static void setTheme(Context context, int theme) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("Theme", theme);
        Log.i(TAG, "setTheme:" + theme);
        editor.apply();
    }

    @SuppressLint("CommitPrefEdits")
    public static int getTheme(Context context) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        int theme = sp.getInt("Theme", 1);
        Log.i(TAG, "getTheme:" + theme);
        return theme;
    }

    public static void setLoginState(Context context, boolean loginState) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("loginState", loginState);
        Log.i(TAG, "setLoginState:" + loginState);
        editor.apply();
    }

    @SuppressLint("CommitPrefEdits")
    public static boolean getLoginState(Context context) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        boolean loginState = sp.getBoolean("loginState", false);
        Log.i(TAG, "getLoginState:" + loginState);
        return loginState;
    }

    public void clearALL() {
        editor.clear();
        editor.commit();
    }

}
