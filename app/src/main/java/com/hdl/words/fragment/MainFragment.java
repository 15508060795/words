package com.hdl.words.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hdl.words.R;
import com.hdl.words.adapter.FragmentPagerViewAdapter;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.main.ReciteFragment;
import com.hdl.words.fragment.main.SettingFragment;
import com.hdl.words.fragment.main.TranslateFragment;
import com.hdl.words.utils.ToastHelper;
import com.qmuiteam.qmui.widget.QMUIPagerAdapter;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.QMUIViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Date 2018/8/2 9:56
 * auther HDL
 * Mail 229101253@qq.com
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.pager)
    QMUIViewPager viewPager;
    @BindView(R.id.bottomBar)//底部状态栏
            QMUITabSegment tabSegment;
    private ArrayList<BaseFragment>fragmentList=new ArrayList<>(3);
    private int[] tabs=new int[]{R.string.translate,R.string.recite,R.string.personal_center};
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static MainFragment newInstance(){
        MainFragment fragment=new MainFragment();
        return  fragment;
    }
    public static MainFragment newInstance(Bundle bundle){
        MainFragment fragment=new MainFragment();
        fragment.setArguments(bundle);
        return  fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void initData() {
        fragmentList.add(FIRST,TranslateFragment.newInstance());
        fragmentList.add(SECOND,ReciteFragment.newInstance());
        fragmentList.add(THIRD,SettingFragment.newInstance());
/*        if(findChildFragment(TranslateFragment.class)==null){
            fragmentList.add(FIRST,TranslateFragment.newInstance());
            fragmentList.add(SECOND,ReciteFragment.newInstance());
            fragmentList.add(THIRD,SettingFragment.newInstance());
            loadMultipleRootFragment(R.id.pager,
                    FIRST,
                    fragmentList.get(FIRST),
                    fragmentList.get(SECOND),
                    fragmentList.get(THIRD)
            );
        }else{
            fragmentList.set(FIRST,findChildFragment(TranslateFragment.class));
            fragmentList.set(SECOND,findChildFragment(TranslateFragment.class));
            fragmentList.set(THIRD,findChildFragment(TranslateFragment.class));
        }*/
        tabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_bottomBar_normal_bg));
        tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.white));
        tabSegment.setBackgroundColor(getResources().getColor(R.color.color_bottomBar_select_bg));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_translate),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_translate),
                getActivity().getResources().getString(R.string.translate), true));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_recite),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_recite),
                getActivity().getResources().getString(R.string.recite), true));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_personal_center),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_personal_center),
                getActivity().getResources().getString(R.string.personal_center), true));
        tabSegment.selectTab(0);
        tabSegment.setupWithViewPager(viewPager);
        viewPager.setAdapter(new FragmentPagerViewAdapter(getChildFragmentManager(),fragmentList));
        viewPager.setCurrentItem(FIRST);
    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {

    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) { start(targetFragment); }
    public void startBrotherFragmentAndPop(SupportFragment targetFragment) { startWithPop(targetFragment); }

}

