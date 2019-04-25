package com.hdl.words.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdl.words.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date 2019/4/17 15:17
 * author hdl
 * Description:右侧字母搜索栏
 */
public class SlideBarSearchView extends LinearLayout implements View.OnTouchListener {
    /*数据集合*/
    private List<String> mData;
    private Context mContext;
    private OnItemTouchListener onItemTouchListener;
    /*默认选中位置*/
    private int mSelectedIndex;
    /*默认字体颜色*/
    private int mDefaultColor;
    /*选中字体颜色*/
    private int mSelectedColor;
    /*字体大小*/
    private int mTextSize;

    public SlideBarSearchView(Context context) {
        this(context, null, 0, 0);
    }

    public SlideBarSearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public SlideBarSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlideBarSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mData = new ArrayList<>();
        String[] DEFAULT_DATA = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"
        };
        mData.addAll(Arrays.asList(DEFAULT_DATA));
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        setOnTouchListener(this);
        for (String item : mData) {
            addView(createView(item));
        }
    }

    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlideBarSearchView);
        mDefaultColor = array.getColor(R.styleable.SlideBarSearchView_tv_default_color, Color.BLACK);
        mSelectedColor = array.getColor(R.styleable.SlideBarSearchView_tv_selected_color, Color.GREEN);
        mTextSize = array.getColor(R.styleable.SlideBarSearchView_tv_size, 14);
        mSelectedIndex = array.getInteger(R.styleable.SlideBarSearchView_selected_index, 0);
        array.recycle();
    }

    private TextView createView(final String item) {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        view.setGravity(Gravity.CENTER);
        view.setText(item);
        if (mData.indexOf(item) == mSelectedIndex) {
            view.setTextColor(mSelectedColor);
        } else {
            view.setTextColor(mDefaultColor);
        }
        view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        return view;
    }

    private void changeTextViewColor(int index) {
        if (index == mSelectedIndex)
            return;
        mSelectedIndex = index;
        for (int i = 0; i < getChildCount(); i++) {
            if (i == mSelectedIndex) {
                ((TextView) getChildAt(i)).setTextColor(mSelectedColor);
            } else {
                ((TextView) getChildAt(i)).setTextColor(mDefaultColor);
            }
        }
    }

    public void setData(List<String> list) {
        mData.clear();
        mData.addAll(list);
        removeAllViews();
        for (String item : list) {
            addView(createView(item));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                transformValue(this, event);
                break;
            case MotionEvent.ACTION_MOVE:
                transformValue(this, event);
                break;
            case MotionEvent.ACTION_UP:
                transformValue(this, event);
                break;
        }
        return true;
    }

    private void transformValue(LinearLayout layout, MotionEvent event) {
        TextView firstView = (TextView) layout.getChildAt(0);
        float firstViewTop = firstView.getTop();
        float itemHeight = firstView.getHeight();
        float currentY = event.getY();
        if (currentY >= 0 && currentY <= getHeight()) {
            if (currentY >= firstViewTop && currentY <= itemHeight * layout.getChildCount() + firstViewTop) {
                int index = (int) ((currentY - firstViewTop) / itemHeight);
                if (onItemTouchListener != null) {
                    onItemTouchListener.onItemTouch(index, mData.get(index));
                }
                changeTextViewColor(index);
                invalidate();
            }
        }

    }

    public interface OnItemTouchListener {
        void onItemTouch(int pos, String item);
    }

    public void setOnItemTouchListener(OnItemTouchListener onItemClickListener) {
        this.onItemTouchListener = onItemClickListener;
    }

    public int getIndex() {
        return mSelectedIndex;
    }

    public void setIndex(int index) {
        this.mSelectedIndex = index;
    }
}
