package com.hdl.words.fragment.main;

import android.os.Bundle;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;


/**
 * Created by HDL on 2018/1/9.
 */

public class ContactsFragment extends BaseFragment {
    public static ContactsFragment newInstance(Bundle bundle){
        ContactsFragment fragment=new ContactsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_contacts;
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