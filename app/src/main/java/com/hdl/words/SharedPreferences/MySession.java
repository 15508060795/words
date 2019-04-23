package com.hdl.words.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MySession {
    private static SharedPreferences sp;
    private static final String sp_name = "sp_demo";
    private static SharedPreferences.Editor editor;
    protected static final String TAG = "SharedPreferences";

    public static void setLoginState(Context context, boolean loginState) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("loginState", loginState);
        Log.i(TAG, "setLoginState:" + loginState);
        editor.apply();
    }

    public static boolean getLoginState(Context context) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        boolean loginState = sp.getBoolean("loginState", false);
        Log.i(TAG, "getLoginState:" + loginState);
        return loginState;
    }

    public static void setUsername(Context context, String username) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("username", username);
        Log.i(TAG, "setUsername:" + username);
        editor.apply();
    }

    public static String getUsername(Context context) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        String username = sp.getString("username", "null username");
        Log.i(TAG, "getUsername:" + username);
        return username;
    }

    public static void clear(Context context) {
        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
        Log.i(TAG, "清除用户信息");
    }

}
