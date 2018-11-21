package com.hdl.words.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.hdl.words.R;
import com.hdl.words.base.BaseActivity;

import java.util.Random;

import butterknife.BindView;

public class FlashActivity extends BaseActivity {
    @BindView(R.id.img_flash)
    ImageView flashImg;
    int[] image;
    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_flash;
    }

    @Override
    public void initData() {
        image=new int[]{
                R.mipmap.ic_flash_bg1,
                R.mipmap.ic_flash_bg2,
                R.mipmap.ic_flash_bg3,
                R.mipmap.ic_flash_bg4,
                R.mipmap.ic_flash_bg5
        };
        int code=new Random().nextInt(5);
        flashImg.setImageResource(image[code]);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAndCloseThis(MainActivity.class);
            }
        },2000);
    }

    @Override
    protected void initTopBar() {

    }
}
