package com.hdl.words.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.main.ReciteFragment;
import com.hdl.words.fragment.main.SettingFragment;
import com.hdl.words.fragment.main.TranslateFragment;
import com.qmuiteam.qmui.widget.QMUITabSegment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Date 2018/8/2 9:56
 * author HDL
 * Mail 229101253@qq.com
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.bottomBar)//底部状态栏
            QMUITabSegment mTabSegment;
    private BaseFragment[] fragments = new BaseFragment[3];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    public static MainFragment newInstance() {
        return new MainFragment();
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
        if (findChildFragment(TranslateFragment.class) == null) {
            fragments[FIRST] = TranslateFragment.newInstance();
            fragments[SECOND] = ReciteFragment.newInstance();
            fragments[THIRD] = SettingFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_main_fragment, FIRST, fragments[FIRST], fragments[SECOND], fragments[THIRD]);
        } else {
            fragments[FIRST] = findChildFragment(TranslateFragment.class);
            fragments[SECOND] = findChildFragment(ReciteFragment.class);
            fragments[THIRD] = findChildFragment(SettingFragment.class);
        }
        mTabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_bottomBar_normal));
        mTabSegment.setDefaultSelectedColor(getResources().getColor(R.color.color_bottomBar_select));
        mTabSegment.setBackgroundColor(getResources().getColor(R.color.color_bottomBar_bg));
        mTabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_translate),
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_translate),
                getString(R.string.translate), true));
        mTabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_recite),
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_recite),
                getString(R.string.recite), true));
        mTabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_personal_center),
                ContextCompat.getDrawable(_mActivity, R.mipmap.ic_bottom_personal_center),
                getString(R.string.personal_center), true));
        mTabSegment.selectTab(0);
    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {
        mTabSegment.addOnTabSelectedListener(new QMUITabSegment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                showHideFragment(fragments[index]);
            }

            @Override
            public void onTabUnselected(int index) {

            }

            @Override
            public void onTabReselected(int index) {

            }

            @Override
            public void onDoubleTap(int index) {

            }
        });
    }

    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment fragment) {
        start(fragment);
    }

    public void startBrotherFragmentAndPop(SupportFragment fragment) {
        startWithPop(fragment);
    }
}

