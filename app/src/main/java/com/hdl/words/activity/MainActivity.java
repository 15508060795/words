package com.hdl.words.activity;

import android.os.Bundle;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseActivity;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.fragment.MainFragment;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;


public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initParams(Bundle params) {

    }
    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void initTopBar() {

    }
    @Override
    public void initData() {
        setFragmentAnimator(new DefaultHorizontalAnimator());
        if(MySession.getLoginState(this)){
            loadRootFragment(R.id.fl_main,MainFragment.newInstance());
        }else{
            loadRootFragment(R.id.fl_main,LoginFragment.newInstance());
        }
    }
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }
    public void startBrotherFragmentAndPop(SupportFragment targetFragment) {
        startWithPop(targetFragment);
    }

}
