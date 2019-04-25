package com.hdl.words.weight;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Date 2019/4/25 19:05
 * author hdl
 * Description:
 */
public class DatePickerView extends AlertDialog {

    protected DatePickerView(Context context) {
        super(context);
    }

    protected DatePickerView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected DatePickerView(Context context, int themeResId) {
        super(context, themeResId);
    }
}
