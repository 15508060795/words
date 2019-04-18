package com.hdl.words.view.main;

/**
 * Date 2019/3/3 14:58
 * author HDL
 * Description:
 */
public interface ITranslateView {
    void languageSwitch();

    void translateResult(String result,boolean fromUser);

    void fromTypeChange();

    void toTypeChange();

    void showToast(int resId);
}
