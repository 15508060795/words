package com.hdl.words.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by HDL on 2018/1/16.
 */

public abstract class BaseFragment extends SupportFragment {
    protected Activity mActivity;
    protected View root;
    protected boolean visible;
    protected final String TAG = this.getClass().getSimpleName();
    protected Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG, "onCreateView()");
        if(root==null) {
            root = LayoutInflater.from(mActivity).inflate(bindLayout(), null);
            ButterKnife.bind(this, root);
            initTopBar();
            initData();
            initListener();
            FragmentCollector.addFragment(this);
        }else{
            ViewGroup parent = (ViewGroup) root.getParent();
            if (parent != null) {
                parent.removeView(root);
            }
        }
        return root;
    }
    public abstract int bindLayout();
    public abstract void initTopBar();
    public abstract void initData();
    public abstract void initListener();
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mActivity=(Activity)context;
        bundle=getArguments();
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "onStart()");
    }
    @Override
    public void onResume() {
        super.onResume();
        visible=true;
        Log.w(TAG, "onResume()visible:"+visible);
    }
    @Override
    public void onPause() {
        super.onPause();
        hideSoftInput();
        Log.w(TAG, "onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();
        visible=false;
        hideSoftInput();
        Log.w(TAG, "onStop()visible:"+visible);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideSoftInput();
        Log.w(TAG, "onDestroyView()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentCollector.removeFragment(this);
        hideSoftInput();
        Log.w(TAG, "onDestroy()");
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            visible=false;
            Log.i(TAG,TAG+"不在前端显示,visible:"+visible);
            return;
        }else{  // 在最前端显示 相当于调用了onResume();
            visible=true;
            //网络数据刷新
        }
    }
//    public int getThemeColor(){
//        TypedValue typedValue = new TypedValue();
//        mActivity.getTheme().resolveAttribute(R.attr.colorPrimary,typedValue,true);
//        return typedValue.data;
//    }


}
