package com.hdl.words.fragment.main.setting;

import android.os.Bundle;
import android.view.View;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportHelper;

/**
 * Date 2018/12/11 21:11
 * author HDL
 * Mail 229101253@qq.com
 */
public class PersonalDataFragment extends BaseFragment {
   /* @BindView(R.id.topBar)
    QMUITopBar mTopBar;*/
    /*@BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;*/

    public static PersonalDataFragment newInstance(Bundle bundle) {
        PersonalDataFragment fragment = new PersonalDataFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_personal_data;
    }

    @Override
    public void initTopBar() {
        /*mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mTopBar.addRightTextButton("SDA", 0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupportHelper.showFragmentStackHierarchyView((SupportActivity) _mActivity);
            }
        });
        mCollapsingTopBarLayout.setTitle(getString(R.string.personal_data));*/
    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);
    }

    @Override
    public void initListener() {

    }
}
