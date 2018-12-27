package com.hdl.words.fragment.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.view.DashboardView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;


/**
 * Created by HDL on 2018/11/9.
 */


public class ReciteFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.rv_recite)
    RecyclerView mReciteRv;
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
        return R.layout.fragment_main_recite;
    }



    @Override
    public void initData() {
        mReciteRv.setLayoutManager(new GridLayoutManager(_mActivity,3));
        //mReciteRv.setAdapter();
    }

    @Override
    public void initTopBar() {
        mTopBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        mTopBar.setTitle(R.string.recite).setTextColor(getResources().getColor(R.color.color_topBar_title));
    }

    @Override
    public void initListener() {

    }

}