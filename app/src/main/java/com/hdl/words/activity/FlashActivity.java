package com.hdl.words.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.LruCache;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdl.words.R;
import com.hdl.words.base.BaseActivity;

import java.util.Random;

import butterknife.BindView;

public class FlashActivity extends BaseActivity {
    @BindView(R.id.img_flash)
    ImageView mFlashImg;
    @BindView(R.id.tv_flash)
    TextView mFlashTv;

    @Override
    public int bindLayout() {
        return R.layout.activity_flash;
    }

    @Override
    public void initParams(Bundle params) {
        setAllowFullScreen(true);

    }

    @Override
    protected void initTopBar() {

    }

    @Override
    public void initData() {
        int[] image = new int[]{
                R.mipmap.ic_flash_bg1,
                R.mipmap.ic_flash_bg2,
                R.mipmap.ic_flash_bg3,
                R.mipmap.ic_flash_bg4,
                R.mipmap.ic_flash_bg5
        };
        int[] msg = new int[]{
                R.string.flash_note1,
                R.string.flash_note2,
                R.string.flash_note3,
                R.string.flash_note4,
                R.string.flash_note5,
                R.string.flash_note6,
                R.string.flash_note7,
                R.string.flash_note8,
                R.string.flash_note9,
                R.string.flash_note0
        };
        randomInit(image,msg);
        new Handler().postDelayed(() -> startActivityAndCloseThis(MainActivity.class), 2000);
    }
    private void randomInit(int[] image,int[] msg){
        int flashCode = new Random().nextInt(5);
        mFlashImg.setImageResource(image[flashCode]);
        int msgCode = new Random().nextInt(10);
        mFlashTv.setText(getString(msg[msgCode]));
    }
}
