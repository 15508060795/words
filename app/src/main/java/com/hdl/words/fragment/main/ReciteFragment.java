package com.hdl.words.fragment.main;

import android.os.Bundle;
import android.widget.SeekBar;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.view.CarDoorAngleDashboard;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;


/**
 * Created by HDL on 2018/1/9.c
 */

public class ReciteFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.seekBar2)
    CarDoorAngleDashboard dashboardView1;
    @BindView(R.id.seekBar6)
    SeekBar seekBar;
    public static ReciteFragment newInstance(){
        ReciteFragment fragment=new ReciteFragment();
        return fragment;
    }
    public static ReciteFragment newInstance(Bundle bundle){
        ReciteFragment fragment=new ReciteFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_main_recites;
    }



    @Override
    public void initData() {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
                dashboardView1.setRealTimeValue(50+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void initTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        topBar.setTitle(R.string.recite).setTextColor(getResources().getColor(R.color.color_topBar_title_white));
    }

    @Override
    public void initListener() {

    }

}