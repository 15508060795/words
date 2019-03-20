package com.hdl.words.view;

/**
 * Date 2019/3/2 16:07
 * author HDL
 * Description:
 */
public interface IForgetView {
    void setNull();

    void showLoading(int resId);

    void changePasswordSuccess(String msg);

    void changePasswordFail(String msg);

    void showToast(int resId);

    void hideDialog();
}
