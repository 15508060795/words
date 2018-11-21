package com.hdl.words.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import com.hdl.words.R;
import com.hdl.words.base.BaseActivity;
import java.util.Random;
import butterknife.BindView;

public class FlashActivity extends BaseActivity {
    @BindView(R.id.img_flash)
    ImageView flashImg;
    @BindView(R.id.tv_flash)
    TextView flashTv;
    int[] image;
    int[] msg;
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
        msg=new int[]{
                R.string.flash_note1,
                R.string.flash_note2,
                R.string.flash_note3,
                R.string.flash_note4,
                R.string.flash_note5,
                R.string.flash_note6,
                R.string.flash_note7,
                R.string.flash_note8,
                R.string.flash_note9,
                R.string.flash_note10
        };
        int flashCode=new Random().nextInt(5);
        flashImg.setImageResource(image[flashCode]);
        int msgCode=new Random().nextInt(10);
        flashTv.setText(getString(msg[msgCode]));
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
