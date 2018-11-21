package com.hdl.words.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseActivity;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.fragment.MainFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_main)
    FrameLayout mainFl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        if(MySession.getLoginState(this)){
            MainFragment mainFragment=MainFragment.newInstance();
            loadRootFragment(R.id.fl_main,mainFragment);
        }else{
            LoginFragment loginFragment=LoginFragment.newInstance();
            loadRootFragment(R.id.fl_main,loginFragment);
        }
    }
    @Override
    protected void initTopBar() {

    }
}
