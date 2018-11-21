package com.hdl.words.fragment.main;

import android.os.Bundle;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;


/**
 * Created by HDL on 2018/1/9.
 */

public class TidingsFragment extends BaseFragment {

    public static TidingsFragment newInstance(Bundle bundle){
        TidingsFragment fragment=new TidingsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_tidings;
    }

    @Override
    public void initData() {


    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {

    }

}
