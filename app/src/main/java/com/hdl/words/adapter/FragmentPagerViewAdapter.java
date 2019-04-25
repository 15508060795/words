package com.hdl.words.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hdl.words.base.BaseFragment;

public class FragmentPagerViewAdapter extends FragmentPagerAdapter {
    private BaseFragment[] fragments;

    public FragmentPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentPagerViewAdapter(FragmentManager fm, BaseFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

}
