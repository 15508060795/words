package com.hdl.words.fragment.main;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.PersonalInfoBean;
import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.fragment.MainFragment;
import com.hdl.words.fragment.main.setting.PersonalInfoFragment;
import com.hdl.words.model.IGETPersonalInfoResult;
import com.hdl.words.model.PersonalInfoModelImpl;
import com.hdl.words.presenter.main.setting.PersonalInfoPresenterImpl;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.w3c.dom.Text;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HDL on 2018/11/9.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.tv_username)
    TextView mUsernameTv;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.lv_setting)
    QMUIGroupListView mGroupListView;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    public static SettingFragment newInstance(Bundle bundle) {
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
        String username = MySession.getUsername(_mActivity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETPersonalInfoResult request = retrofit.create(IGETPersonalInfoResult.class);
        Call<PersonalInfoBean> call = request.getCall(username);
        call.enqueue(new Callback<PersonalInfoBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PersonalInfoBean> call, @NonNull Response<PersonalInfoBean> response) {
                try {
                    PersonalInfoBean.DataBean bean = response.body().getData();
                    PersonalInfoModelImpl.getInstance().setBean(bean);
                    refreshUI(PersonalInfoModelImpl.getInstance().getBean());
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PersonalInfoBean> call, @NonNull Throwable t) {
                Log.e(TAG, "网络错误");
            }
        });


        QMUICommonListItemView personalData = mGroupListView.createItemView("个人资料");
        personalData.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        personalData.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("account", "account");
            assert getParentFragment() != null;
            Log.e("getParentFragment", getParentFragment() + "  ");
            ((MainFragment) getParentFragment()).startBrotherFragment(PersonalInfoFragment.newInstance(bundle));
        });
        QMUICommonListItemView logout = mGroupListView.createItemView("退出登录");
        logout.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        logout.setOnClickListener(v -> {
            MySession.setLoginState(_mActivity, false);
            new QMUIDialog.MessageDialogBuilder(_mActivity)
                    .setMessage(R.string.dialog_logout_message)
                    .addAction(0, R.string.logout, QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
                        dialog.dismiss();
                        try {
                            ((MainFragment) getParentFragment()).startBrotherFragmentAndPop(LoginFragment.newInstance());
                            MySession.clear(_mActivity);
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    })
                    .addAction(R.string.cancel, (dialog, index) -> dialog.dismiss())
                    .show();
        });
        QMUIGroupListView.newSection(_mActivity)
                //.setTitle("Section 1: 默认提供的样式")
                //.setDescription("Section 1 的描述")
                //.addItemView(immersion,null)
                .addItemView(personalData, null)
                .addItemView(logout, null)
                .addTo(mGroupListView);
    }

    @Override
    public void initTopBar() {
        mTopBar.setTitle(R.string.personal_center);
    }

    @Override
    public void initListener() {
    }

    private void refreshUI(PersonalInfoBean.DataBean bean) {
        mUsernameTv.setText(bean.getUsername());
        mNameTv.setText(bean.getName());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (null != PersonalInfoModelImpl.getInstance().getBean()) {
                refreshUI(PersonalInfoModelImpl.getInstance().getBean());
            }
        }
    }
}
