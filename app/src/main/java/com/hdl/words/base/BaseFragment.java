package com.hdl.words.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by HDL on 2018/1/16.
 */

public abstract class BaseFragment extends SwipeBackFragment {
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();
    protected Bundle mBundle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBundle = getArguments();
        Log.w(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate()");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.w(TAG, "onCreateView()");
        setSwipeBackEnable(false);
        if (mRootView == null) {
            mRootView = LayoutInflater.from(_mActivity).inflate(bindLayout(), null);
            ButterKnife.bind(this, mRootView);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.w(TAG, "onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.w(TAG, "onResume()");
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
        hideSoftInput();
        Log.w(TAG, "onStop()");
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
        hideSoftInput();
        Log.w(TAG, "onDestroy()");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initTopBar();
        initData();
        initListener();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        hideSoftInput();
        Log.w(TAG, "onDetach()");
    }

    public abstract int bindLayout();

    public abstract void initTopBar();

    public abstract void initData();

    public abstract void initListener();

}
