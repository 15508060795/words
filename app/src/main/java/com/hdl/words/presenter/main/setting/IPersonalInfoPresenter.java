package com.hdl.words.presenter.main.setting;

/**
 * Date 2019/4/25 23:32
 * author hdl
 * Description:
 */
public interface IPersonalInfoPresenter {
    void setName(String str);

    void setSex(String str);

    void setBirth(String str);

    void setHometown(String str);

    void setSign(String str);

    void requestDate(String username);

    void changeInfo(int pos);
}
