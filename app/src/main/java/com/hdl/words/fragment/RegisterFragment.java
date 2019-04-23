package com.hdl.words.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.presenter.RegisterPresenterImpl;
import com.hdl.words.utils.QmuiDialogHelper;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.IRegisterView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * time: 11/1/2018
 * author: CHe
 */
public class RegisterFragment extends BaseFragment implements IRegisterView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.et_account)
    EditText mAccountEt;
    @BindView(R.id.et_password)
    EditText mPasswordEt;
    @BindView(R.id.et_rePassword)
    EditText mRePasswordEt;
    private RegisterPresenterImpl mPresenter;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @OnClick({R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                String account = mAccountEt.getText().toString().trim();
                String password1 = mPasswordEt.getText().toString().trim();
                String password2 = mRePasswordEt.getText().toString().trim();
                mPresenter.register(account, password1, password2);
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initTopBar() {
        mTopBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mTopBar.setTitle(R.string.register_title);

    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void showToast(int resId) {
        ToastHelper.shortToast(_mActivity, resId);
    }

    @Override
    public void showLoadingDialog(int resId) {
        QmuiDialogHelper.showLoading(_mActivity, resId);
    }

    @Override
    public void registerSucceed(String msg, final String username) {
        QmuiDialogHelper.showSuccess(_mActivity, msg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MySession.setUsername(_mActivity,username);
                MySession.setLoginState(_mActivity,true);
                startWithPop(MainFragment.newInstance());
                hideDialog();
            }
        }, 1500);

    }

    @Override
    public void registerFail(String msg) {
        QmuiDialogHelper.showFail(_mActivity, msg, 1000);
    }

    @Override
    public void hideDialog() {
        QmuiDialogHelper.hide();
    }

}
