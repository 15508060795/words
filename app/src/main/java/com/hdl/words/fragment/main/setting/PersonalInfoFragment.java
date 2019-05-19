package com.hdl.words.fragment.main.setting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hdl.words.Beans.PersonalInfoBean;
import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.presenter.main.setting.PersonalInfoPresenterImpl;
import com.hdl.words.view.main.setting.IPersonalInfoView;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date 2018/12/11 21:11
 * author HDL
 * Mail 229101253@qq.com
 */
public class PersonalInfoFragment extends BaseFragment implements IPersonalInfoView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.lv_setting)
    QMUIGroupListView mSettingLv;
    private List<QMUICommonListItemView> mItemList;
    private PersonalInfoPresenterImpl presenter;
    public static PersonalInfoFragment newInstance(Bundle bundle) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_personal_data;
    }

    @Override
    public void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> pop());
        mCollapsingTopBarLayout.setTitle(getString(R.string.personal_data));
    }

    @Override
    public void initData() {
        presenter = new PersonalInfoPresenterImpl(this);
        String username = MySession.getUsername(_mActivity);
        presenter.requestDate(username);
        mItemList = new ArrayList<>();
        setSwipeBackEnable(true);
        QMUICommonListItemView account = mSettingLv.createItemView("用户名");
        account.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        account.setDetailText(username);

        QMUICommonListItemView name = mSettingLv.createItemView("昵称");
        name.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        name.setDetailText("***");

        QMUICommonListItemView sex = mSettingLv.createItemView("性别");
        sex.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        sex.setDetailText("***");

        QMUICommonListItemView birthday = mSettingLv.createItemView("生日");
        birthday.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        birthday.setDetailText("****-**-**");

        QMUICommonListItemView hometown = mSettingLv.createItemView("故乡");
        hometown.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        hometown.setDetailText("***");

        QMUICommonListItemView sign = mSettingLv.createItemView("个性签名");
        sign.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        sign.setDetailText("***");

        View.OnClickListener click = v -> {
            if (v instanceof QMUICommonListItemView) {
                String str = ((QMUICommonListItemView) v).getText().toString();
                switch (str) {
                    case "昵称":
                        presenter.setName(getDetailText(0));
                        break;
                    case "性别":
                        presenter.setSex(getDetailText(1));
                        break;
                    case "生日":
                        presenter.setBirth(getDetailText(2));
                        break;
                    case "故乡":
                        presenter.setHometown(getDetailText(3));
                        break;
                    case "个性签名":
                        presenter.setSign(getDetailText(4));
                        break;
                }
            }
        };
        QMUIGroupListView.newSection(_mActivity)
                .addItemView(account, click)
                .addItemView(name, click)
                .addItemView(sex, click)
                .addItemView(birthday, click)
                .addItemView(hometown, click)
                .addItemView(sign, click)
                .addTo(mSettingLv);
        mItemList.add(name);
        mItemList.add(sex);
        mItemList.add(birthday);
        mItemList.add(hometown);
        mItemList.add(sign);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void refreshUI(PersonalInfoBean.PersonalInfo bean) {
        mItemList.get(0).setDetailText(bean.getName());
        mItemList.get(1).setDetailText(bean.getSex() == 1 ? "男" : "女");
        mItemList.get(2).setDetailText(bean.getBirth());
        mItemList.get(3).setDetailText(bean.getHometown());
        mItemList.get(4).setDetailText(bean.getSign());
        Log.e(TAG, "UI刷新");
    }

    @Override
    public void showToast(String msg) {

    }

    private String getDetailText(int pos) {
        return mItemList.get(pos).getDetailText().toString();
    }
}
