package com.hdl.words.fragment;

import android.view.View;
import android.widget.EditText;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.presenter.LoginPresenterImpl;
import com.hdl.words.utils.QmuiDialogHelper;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.ILoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date 2018/8/2 10:06
 * author HDL
 * Mail 229101253@qq.com
 */
public class LoginFragment extends BaseFragment implements ILoginView {
    @BindView(R.id.et_login_ac)
    EditText mAccountEt;
    @BindView(R.id.et_login_pw)
    EditText mPasswordEt;
    private LoginPresenterImpl mPresenter;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @OnClick({R.id.btn_login, R.id.tv_login_rePw, R.id.tv_login_newAdm})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String account = mAccountEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                //mPresenter.login(account, password);
                startWithPop(MainFragment.newInstance());
                break;
            case R.id.tv_login_rePw:
                mPresenter.skipForget();
                break;
            case R.id.tv_login_newAdm:
                mPresenter.skipRegister();
                break;
            default:
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initData() {
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void showToast(int resId) {
        ToastHelper.shortToast(_mActivity, resId);
    }

    @Override
    public void skipRegister() {
        start(RegisterFragment.newInstance());
    }

    @Override
    public void skipForget() {
        start(ForgetFragment.newInstance());
    }

    @Override
    public void loginSucceed(String msg) {
        hideDialog();
        startWithPop(MainFragment.newInstance());
    }

    @Override
    public void loginFail(String msg) {
        QmuiDialogHelper.showFail(_mActivity, msg, 1000);
    }

    @Override
    public void showLoadingDialog(int resId) {
        QmuiDialogHelper.showLoading(_mActivity, resId);
    }

    @Override
    public void hideDialog() {
        QmuiDialogHelper.hide();
    }

}
