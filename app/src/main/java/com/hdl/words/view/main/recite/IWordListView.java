package com.hdl.words.view.main.recite;

import android.widget.ImageView;

/**
 * Date 2019/4/23 22:21
 * author hdl
 * Description:
 */
public interface IWordListView {
    void scrollList(int pos);

    void setLike(ImageView img, int pos);

    void setDislike(ImageView img, int pos);

    void showToast(String msg);
}
