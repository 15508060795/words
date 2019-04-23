package com.hdl.words.presenter.main.recite;

import android.widget.ImageView;

/**
 * Date 2019/4/23 22:33
 * author hdl
 * Description:
 */
public interface IWordListPresenter {
    void requestDisLike(String username,String word, ImageView img, int pos);

    void requestLike(String username,String word, ImageView img, int pos);
}
