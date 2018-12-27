package com.hdl.words.fragment.main.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * Date 2018/12/11 21:11
 * author HDL
 * Mail 229101253@qq.com
 */
public class PersonalDataFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.recyclerView)
    ListView recyclerView;
    public static PersonalDataFragment newInstance(Bundle bundle){
        PersonalDataFragment fragment=new PersonalDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_personal_data;
    }

    @Override
    public void initTopBar() {
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        topBar.setTitle("个人资料");
        topBar.addRightTextButton("SDA",0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportHelper.showFragmentStackHierarchyView((SupportActivity)_mActivity);
            }
        });
    }

    @Override
    public void initData() {
        //recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add("asdv");
        }
        ArrayAdapter adapter=new ArrayAdapter<>(_mActivity,R.layout.simple_list_item,list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }
}
