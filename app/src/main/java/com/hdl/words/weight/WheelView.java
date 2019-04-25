package com.hdl.words.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hdl.words.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 2019/4/25 15:30
 * author HDL
 * Description:选择器,基于ScrollView,嵌套LinerLayout,每一个item都是一个TextView,通过滑动,判定位置从而实现回弹.
 * 致谢: Author: wangjie Email: tiantian.china.2@gmail.com Date: 7/1/14.
 */
public class WheelView extends ScrollView {
    public static final String TAG = "WheelView";
    private Context mContext;
    /*嵌套的LinearLayout*/
    private LinearLayout mLayout;
    /*数据list*/
    private List<String> mDataList;
    /*监听器*/
    private OnWheelViewListener mOnWheelViewListener;
    /*每页显示的数量*/
    private int mDisplayItemCount;
    /*当前选中位置*/
    private int mSelectedIndex = 1;
    /*默认字体颜色*/
    private int mDefaultColor;
    /*选中字体颜色*/
    private int mSelectedColor;
    /*分隔线颜色颜色*/
    private int mSeparatorColor;
    /*字体大小*/
    private int mTextSize;
    int initialY;
    private Runnable scrollerTask;
    /*延迟时间*/
    private int mDelayTime = 50;
    public static final int OFFSET_DEFAULT = 1;
    /* 偏移量（需要在最前面和最后面补全）*/
    private int offset = OFFSET_DEFAULT;
    /*子项高度*/
    private int mItemHeight = 0;
    /*记录滑动方向*/
    private int mScrollDirection = -1;
    private static final int SCROLL_DIRECTION_UP = 0;
    private static final int SCROLL_DIRECTION_DOWN = 1;
    private Paint mPaint;
    private float viewWidth;
    /**
     * 获取选中区域的边界
     */
    int[] selectedAreaBorder;

    public WheelView(Context context) {
        this(context, null, 0);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WheelView);
        mDefaultColor = array.getColor(R.styleable.WheelView_wv_tv_default_color, Color.GRAY);
        mSelectedColor = array.getColor(R.styleable.WheelView_wv_tv_selected_color, Color.parseColor("#83cde6"));
        mSeparatorColor = array.getColor(R.styleable.WheelView_wv_separator_color, Color.parseColor("#83cde6"));
        mTextSize = array.getColor(R.styleable.WheelView_wv_tv_size, 20);
        mSelectedIndex = array.getInteger(R.styleable.WheelView_wv_selected_index, 0);
        array.recycle();
    }

    public List<String> getDataList() {
        return mDataList;
    }

    public void setItems(List<String> list) {
        if (null == mDataList) {
            mDataList = new ArrayList<>();
        }
        mDataList.clear();
        mDataList.addAll(list);
        /* 前面和后面补全   填充" "*/
        for (int i = 0; i < offset; i++) {
            mDataList.add(0, "");
            mDataList.add("");
        }
        initData();
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private void init(Context context) {
        this.mContext = context;
        Log.d(TAG, "parent: " + this.getParent());
        this.setVerticalScrollBarEnabled(false);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        mLayout = new LinearLayout(context);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        this.addView(mLayout);

        scrollerTask = () -> {
            int newY = getScrollY();
            if (initialY - newY == 0) { // stopped
                final int remainder = initialY % mItemHeight;
                final int divided = initialY / mItemHeight;
                if (remainder == 0) {
                    mSelectedIndex = divided + offset;
                    onSelectedCallBack();
                } else {
                    if (remainder > mItemHeight / 2) {
                        WheelView.this.post(() -> {
                            WheelView.this.smoothScrollTo(0, initialY - remainder + mItemHeight);
                            mSelectedIndex = divided + offset + 1;
                            onSelectedCallBack();
                        });
                    } else {
                        WheelView.this.post(() -> {
                            WheelView.this.smoothScrollTo(0, initialY - remainder);
                            mSelectedIndex = divided + offset;
                            onSelectedCallBack();
                        });
                    }
                }
            } else {
                initialY = getScrollY();
                WheelView.this.postDelayed(scrollerTask, mDelayTime);
            }
        };
    }


    public void startScrollerTask() {
        initialY = getScrollY();
        this.postDelayed(scrollerTask, mDelayTime);
    }

    private void initData() {
        mDisplayItemCount = offset * 2 + 1;
        for (String item : mDataList) {
            mLayout.addView(createView(item));
        }
        refreshItemView(0);
    }

    private TextView createView(String item) {
        TextView tv = new TextView(mContext);
        tv.setLayoutParams(
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        tv.setSingleLine(true);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        tv.setText(item);
        tv.setGravity(Gravity.CENTER);
        int padding = dip2px(15);
        tv.setPadding(padding, padding, padding, padding);
        if (0 == mItemHeight) {
            mItemHeight = getViewMeasuredHeight(tv);
            Log.d(TAG, "mItemHeight: " + mItemHeight);
            mLayout.setLayoutParams(
                    new LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            mItemHeight * mDisplayItemCount
                    )
            );
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.getLayoutParams();
            this.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            lp.width,
                            mItemHeight * mDisplayItemCount
                    )
            );
        }
        return tv;
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        refreshItemView(t);
        if (t > oldt) {
            Log.d(TAG, "向下滚动");
            mScrollDirection = SCROLL_DIRECTION_DOWN;
        } else {
            Log.d(TAG, "向上滚动");
            mScrollDirection = SCROLL_DIRECTION_UP;
        }
    }

    private void refreshItemView(int y) {
        int position = y / mItemHeight + offset;
        int remainder = y % mItemHeight;
        int divided = y / mItemHeight;

        if (remainder == 0) {
            position = divided + offset;
        } else {
            if (remainder > mItemHeight / 2) {
                position = divided + offset + 1;
            }

        }
        int childSize = mLayout.getChildCount();
        for (int i = 0; i < childSize; i++) {
            TextView itemView = (TextView) mLayout.getChildAt(i);
            if (null == itemView) {
                return;
            }
            if (position == i) {
                itemView.setTextColor(mSelectedColor);
            } else {
                itemView.setTextColor(mDefaultColor);
            }
        }
    }

    private int[] obtainSelectedAreaBorder() {
        if (null == selectedAreaBorder) {
            selectedAreaBorder = new int[2];
        }
        selectedAreaBorder[0] = mItemHeight * offset;
        selectedAreaBorder[1] = mItemHeight * (offset + 1);

        Log.e("AreaBorder", selectedAreaBorder[0] + "  " + selectedAreaBorder[1]);
        return selectedAreaBorder;
    }


    @Override
    public void setBackground(Drawable background) {
        if (viewWidth == 0) {
            viewWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
            Log.d(TAG, "viewWidth: " + viewWidth);
        }

        if (null == mPaint) {
            mPaint = new Paint();
            mPaint.setColor(mSeparatorColor);
            mPaint.setStrokeWidth(dip2px(1f));
        }
        background = new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawLine(
                        viewWidth * 1 / 3,
                        obtainSelectedAreaBorder()[0],
                        viewWidth * 2 / 3,
                        obtainSelectedAreaBorder()[0],
                        mPaint
                );
                canvas.drawLine(
                        viewWidth / 3,
                        obtainSelectedAreaBorder()[1],
                        viewWidth * 2 / 3,
                        obtainSelectedAreaBorder()[1],
                        mPaint
                );
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        };

        super.setBackground(background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "w: " + w + ", h: " + h + ", oldw: " + oldw + ", oldh: " + oldh);
        viewWidth = w;
        setBackground(null);
    }

    /**
     * 选中回调
     */
    private void onSelectedCallBack() {
        if (null != mOnWheelViewListener) {
            mOnWheelViewListener.onSelected(mSelectedIndex, mDataList.get(mSelectedIndex));
        }

    }

    public void setSeletion(int position) {
        final int p = position;
        mSelectedIndex = p + offset;
        this.post(new Runnable() {
            @Override
            public void run() {
                WheelView.this.smoothScrollTo(0, p * mItemHeight);
            }
        });

    }

    /*获取目前选中的Item*/
    public String getSeletedItem() {
        return mDataList.get(mSelectedIndex);
    }

    /*获取目前选中的index*/
    public int getSeletedIndex() {
        return mSelectedIndex - offset;
    }


    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    private int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getViewMeasuredHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
        return view.getMeasuredHeight();
    }

    public interface OnWheelViewListener {
        void onSelected(int pos, String item);
    }

    public OnWheelViewListener getOnWheelViewListener() {
        return mOnWheelViewListener;
    }

    public void setOnWheelViewListener(OnWheelViewListener onWheelViewListener) {
        this.mOnWheelViewListener = onWheelViewListener;
    }
}
