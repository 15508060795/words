package com.hdl.words.presenter.main;

/**
 * Date 2019/3/3 15:01
 * author HDL
 * Description:
 */
public interface ITranslatePresenter {
    void translate(int from, int to, String str);

    void fromTypeChange();

    void toTypeChange();

    void typeSwitch();
}
