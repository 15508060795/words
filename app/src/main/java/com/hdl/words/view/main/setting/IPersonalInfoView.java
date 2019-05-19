package com.hdl.words.view.main.setting;

import com.hdl.words.Beans.PersonalInfoBean;

/**
 * Date 2019/4/12 16:28
 * author hdl
 * Description:
 */
public interface IPersonalInfoView {

    void refreshUI(PersonalInfoBean.PersonalInfo bean);

    void showToast(String msg);
}
