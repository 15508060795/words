package com.hdl.words.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hdl.words.base.BaseFragment;

import java.util.List;

public class FragmentPagerViewAdapter extends FragmentPagerAdapter {
    private  List<BaseFragment>list;
    public FragmentPagerViewAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentPagerViewAdapter(FragmentManager fm, List<BaseFragment>list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
