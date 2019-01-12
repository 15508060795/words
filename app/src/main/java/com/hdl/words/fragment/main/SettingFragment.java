package com.hdl.words.fragment.main;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.fragment.MainFragment;
import com.hdl.words.fragment.main.setting.PersonalDataFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
/**
 * Created by HDL on 2018/11/9.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.lv_setting)
    QMUIGroupListView groupListView;

    public static SettingFragment newInstance(){
        return new SettingFragment();
    }
    public static SettingFragment newInstance(Bundle bundle){
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_personal_center;
    }


    @Override
    public void initData() {
        QMUICommonListItemView personalData = groupListView.createItemView("个人资料");
        personalData.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("account","account");
                assert getParentFragment() != null;
                Log.e("getParentFragment",getParentFragment()+"  ");
                ((MainFragment)getParentFragment()).startBrotherFragment(PersonalDataFragment.newInstance(bundle));
            }
        });
        QMUICommonListItemView logout = groupListView.createItemView("退出登录");
        logout.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySession.setLoginState(_mActivity,false);
                new QMUIDialog.MessageDialogBuilder(_mActivity)
                        .setMessage(R.string.dialog_logout_message)
                        .addAction(0,R.string.logout,QMUIDialogAction.ACTION_PROP_NEGATIVE ,new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                MySession.setLoginState(_mActivity,false);
                                assert getParentFragment() != null;
                                ((MainFragment)getParentFragment()).startBrotherFragmentAndPop(LoginFragment.newInstance());
                            }
                        })
                        .addAction(R.string.cancel, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        QMUIGroupListView.newSection(_mActivity)
                //.setTitle("Section 1: 默认提供的样式")
                //.setDescription("Section 1 的描述")
                //.addItemView(immersion,null)
                .addItemView(personalData,null)
                .addItemView(logout,null)
                .addTo(groupListView);
    }

    @Override
    public void initTopBar() {
        topBar.setTitle(R.string.personal_center);
    }

    @Override
    public void initListener() {
    }

}
