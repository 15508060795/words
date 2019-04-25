package com.hdl.words.view.main.recite;

import com.hdl.words.Beans.WordResultBean;

/**
 * Date 2019/4/12 16:28
 * author hdl
 * Description:
 */
public interface ITypeView {
    void showLoading();

    void dataRequestCompleted(WordResultBean.DataBean bean,int size);

    void showRequestFailDialog();

    void changeWordView(int pos, WordResultBean.DataBean bean,int size);

    void setLike(int pos);

    void setDislike(int pos);

    void showToast(String msg);
}
