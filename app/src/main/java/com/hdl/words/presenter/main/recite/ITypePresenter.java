package com.hdl.words.presenter.main.recite;

/**
 * Date 2019/4/12 16:26
 * author HDL
 * Description:
 */
public interface ITypePresenter {
    void getWords(int type);

    void requestState(String username, int pos);

    void requestLike(String username, int pos);

    void requestDislike(String username, int pos);

    void skipNext(int pos);

    void skipLast(int pos);

    void refreshView(int pos);
}
