package com.hdl.words.fragment;

import android.view.View;
import android.widget.EditText;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.presenter.ForgetPresenterImpl;
import com.hdl.words.utils.QmuiDialogHelper;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.IForgetView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * time: 11/1/2018
 * author: CHe
 */
public class ForgetFragment extends BaseFragment implements IForgetView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.et_account)
    EditText mAccountEt;
    @BindView(R.id.et_prePassword)
    EditText mPrePasswordEt;
    @BindView(R.id.et_nowPassword)
    EditText mNowPasswordEt;
    private ForgetPresenterImpl mPresenter;

    public static ForgetFragment newInstance() {
        return new ForgetFragment();
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String account = mAccountEt.getText().toString().trim();
                String prePassword = mPrePasswordEt.getText().toString().trim();
                String nowPassword = mNowPasswordEt.getText().toString().trim();
                mPresenter.changePassword(account, prePassword, nowPassword);
                break;
            default:
                break;
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_forget;
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
        mTopBar.setTitle(R.string.reset_title);
    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);
        mPresenter = new ForgetPresenterImpl(this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setNull() {
        mAccountEt.setText("");
        mPrePasswordEt.setText("");
        mNowPasswordEt.setText("");
    }

    @Override
    public void showLoading(int resId) {
        QmuiDialogHelper.showLoading(_mActivity, resId);
    }

    @Override
    public void changePasswordSuccess(String msg) {
        setNull();
        QmuiDialogHelper.showSuccess(_mActivity, msg, 1000);
    }

    @Override
    public void changePasswordFail(String msg) {
        QmuiDialogHelper.showFail(_mActivity, msg, 1000);
    }

    @Override
    public void showToast(int resId) {
        ToastHelper.shortToast(_mActivity, resId);
    }

    @Override
    public void hideDialog() {
        QmuiDialogHelper.hide();
    }
}
