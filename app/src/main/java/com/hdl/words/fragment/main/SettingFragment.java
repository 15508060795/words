package com.hdl.words.fragment.main;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.fragment.MainFragment;
import com.hdl.words.utils.QmuiDialogHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;

public class SettingFragment extends BaseFragment {
    @BindView(R.id.lv_setting)
    QMUIGroupListView groupListView;
    public static SettingFragment newInstance(Bundle bundle){
        SettingFragment fragment=new SettingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_setting;
    }


    @Override
    public void initData() {

        QMUICommonListItemView logout=groupListView.createItemView("退出登录");
        logout.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySession.setLoginState(mActivity,false);
                new QMUIDialog.MessageDialogBuilder(mActivity)
                        .setMessage(R.string.dialog_logout_message)
                        .addAction(0,R.string.logout,QMUIDialogAction.ACTION_PROP_NEGATIVE ,new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                MySession.setLoginState(mActivity,false);
                                ((MainFragment)getParentFragment()).startBrotherFragmentAndPop(new LoginFragment());
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
        QMUIGroupListView.newSection(mActivity)
                //.setTitle("Section 1: 默认提供的样式")
                //.setDescription("Section 1 的描述")
                //.addItemView(immersion,null)
                .addItemView(logout,null)
                .addTo(groupListView);
    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {
    }

}
