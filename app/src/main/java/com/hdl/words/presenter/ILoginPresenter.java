package com.hdl.words.presenter;

/**
 * Date 2019/3/2 16:16
 * author HDL
 * Description:
 */
public interface ILoginPresenter {
    void login(String account, String password);

    void skipRegister();

    void skipForget();
}
