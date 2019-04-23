package com.hdl.words.view.main.recite;

/**
 * Date 2019/4/12 16:28
 * author hdl
 * Description:
 */
public interface ITypeView {
    void showLoading();

    void dataRequestCompleted();

    void showRequestFailDialog();

    void changeWordView(int pos);
}
