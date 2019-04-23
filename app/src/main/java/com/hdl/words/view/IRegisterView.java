package com.hdl.words.view;

/**
 * Date 2018/11/1 23:54
 * author HDL
 * Mail 229101253@qq.com
 */
public interface IRegisterView {

    void showToast(int resId);

    void showLoadingDialog(int resId);

    void registerSucceed(String msg,String username);

    void registerFail(String msg);

    void hideDialog();
}
