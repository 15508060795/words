package com.hdl.words.view;

/**
 * Date 2018/11/1 23:48
 * author HDL
 * Mail 229101253@qq.com
 */
public interface ILoginView {

    void showToast(int resId);

    void skipRegister();

    void skipForget();

    void showLoadingDialog(int resId);

    void loginSucceed(String msg, String username);

    void loginFail(String msg);

    void hideDialog();
}
