package com.hdl.words.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * Date 2018/12/27 23:08
 * author HDL
 * Mail 229101253@qq.com
 */
public class WindowInsetsViewPager extends ViewPager {

    public WindowInsetsViewPager(Context context) {
        super(context);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WindowInsetsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                requestApplyInsets();
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });
    }



    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++)
            getChildAt(index).dispatchApplyWindowInsets(insets);
        return insets;
    }

}
