package com.hdl.words.fragment.main;

import android.os.Bundle;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;


/**
 * Created by HDL on 2018/1/9.c
 */

public class ReciteFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
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