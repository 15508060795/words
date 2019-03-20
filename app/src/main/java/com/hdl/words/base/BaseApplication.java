package com.hdl.words.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Demo 的 Application 入口。
 * Created by HDL on 19/1/12.
 */
public class BaseApplication extends Application {

    private Context context;

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);
    }
}
