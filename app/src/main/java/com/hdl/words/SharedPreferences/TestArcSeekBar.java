package com.hdl.words.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
/**
 *
 * TestArcSeekBar
 *
 * @author jay.jiang
 */
public class TestArcSeekBar extends LinearLayout {

    private Context mContext;
    private Paint drawArc;
    private Paint drawDot;
    private CenterPoint mCpoint;
    private Paint drawCircle;
    private float mCircleradius = 300;
    private int mPadding = 75;
    private RectF rectF;
    private float mDotPosition = 0;
    private int mDotRadius = 50;
    private float mThumbX;
    private float mThumbY;
    private double thumbradius;
    private final static float START_ARC = 135;
    private final static float END_ARC = 270;
    private final static float ALL_ARC = 360;
    private int mDotColor;
    private double angle;
    private int view = 425;
    private Paint drawArcShaper;
    private int fromColor;
    private int toColor;
    private boolean isNeedShaper;
    private float[] xy;

    public TestArcSeekBar(Context context) {
        super(context);
        this.mContext = context;
        initPaint();
    }

    public TestArcSeekBar(Context context, AttributeSet set) {
        super(context, set);
        this.mContext = context;
        initPaint();
    }

    @Deprecated
    private TestArcSeekBar(Context context, AttributeSet set, float viewsize) {
        super(context, set);
//                this.mContext = context;
//                view = (int) viewsize;
//                initPaint();
    }

    @SuppressLint("NewApi")
    public TestArcSeekBar(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
        this.mContext = context;
        initPaint();
    }

    // use Paint in this
    private void initPaint() {
        setBackgroundColor(Color.TRANSPARENT);
        setGravity(Gravity.CENTER);

        // all Paint will be init and must be setAntiAlias
        drawArc = new Paint();
        drawArc.setAntiAlias(true);
        drawArcShaper = new Paint();
        drawArcShaper.setAntiAlias(true);
        drawCircle = new Paint();
        drawCircle.setAntiAlias(true);
        drawDot = new Paint();
        drawDot.setAntiAlias(true);
    }

    /**
     * setCenterView
     *
     * @param view
     */
    public void setChildView(View view) {

    }

    /**
     * setCenterViewId
     *
     * @param id
     */
    public void setChildView(int id) {

    }

    /**
     * setDataObj
     *
     * @param obj
     */
    public void setDataObj(Object obj) {

    }

    /**
     * ArcShaperColor
     *
     * @param fromColor
     * @param toColor
     */
    public void setArcShaperColor(int fromColor, int toColor, boolean isNeed) {
        this.fromColor = fromColor;
        this.toColor = toColor;
        this.isNeedShaper = isNeed;
        // getBase Dot
        this.xy = goQuadrant(START_ARC);
    }

    /**
     * setDotCircleRadius
     *
     * @param radius
     */
    public void setDotCircleRadius(int radius) {
        if (mDotRadius >= mPadding) {
            throw new RuntimeException(
                    "The radius must be less than padding param");
        }
        this.mDotRadius = radius;
    }

    /**
     * setArcStrokeWidth
     *
     * @param px
     * @param isNeed
     */
    public void setArcStrokeWidth(int px, boolean isNeed) {
        if (isNeed) {
            drawArc.setStyle(Paint.Style.STROKE);
            drawArc.setStrokeWidth(px);
            drawArcShaper.setStyle(Paint.Style.STROKE);
            drawArcShaper.setStrokeWidth(px + 2);
        }
    }

    /**
     * setDotColorBackground
     *
     * @param color
     */
    public void setDotColorBackground(int color) {
        this.mDotColor = color;
        drawDot.setColor(color);
    }

    /**
     * setDotPosition
     *
     * @param position
     */
    public void setDotPosition(float position) {
        if (position > 100) {
            position = 100;
        }
        if (position < 0) {
            position = 0;
        }
        this.mDotPosition = position;
        initDataforThumb();
    }

    // initDataforThumb move Dot to x y
    private void initDataforThumb() {
        double proportion = (double) (END_ARC / 100f);
        thumbradius = proportion * mDotPosition;
        getPoint(thumbradius, 0);
    }

    // get Quadrant return is DotCircle circle dot
    private float[] goQuadrant(double thumbradius) {
        float a[] = new float[2];
        float arcradius = mCircleradius + mPadding;
        a[0] = arcradius * (float) Math.sin(Math.toRadians(thumbradius));
        a[1] = arcradius * (float) Math.cos(Math.toRadians(thumbradius));
        return a;
    }

    /**
     * setArcColor
     *
     * @param color
     */
    public void setArcColorBackground(int color) {
        drawArc.setColor(color);
    }

    /**
     * setCenterCircleColor
     *
     * @param color
     */
    public void setCenterCircleColorBackground(int color) {
        drawCircle.setColor(color);
    }

    /**
     * setCenterPoint
     *
     * @param point
     */
    public void setCenterPoint(CenterPoint point) {
        this.mCpoint = point;
    }

    /**
     * setCenterCircleRadius
     *
     * @param radius
     */
    public void setCenterCircleRadius(float radius) {
        this.mCircleradius = radius;
    }

    /**
     * setCenterCirclePadding
     *
     * @param padding
     */
    public void setCenterCirclePadding(int padding) {
        this.mPadding = padding;
    }

    /**
     * do OnDraw(Canvas canvas)
     */
    public void doDraw() {
        rectF = new RectF();
        rectF.top = mCpoint.y - (mCircleradius + mPadding);
        rectF.left = mCpoint.x - (mCircleradius + mPadding);
        rectF.right = mCpoint.x + mCircleradius + mPadding;
        rectF.bottom = mCpoint.y + mCircleradius + mPadding;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchThumb(event.getX(), event.getY())) {
                    drawDot.setColor(Color.BLUE);
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                move2radius(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                drawDot.setColor(mDotColor);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    // move point to radius
    private void move2radius(float x, float y) {
        angle = (double) buildingradius(x, y);
        postionAngle(angle);
        if (angle > ALL_ARC - ((ALL_ARC - END_ARC) / 2)) {
            angle = ALL_ARC - ((ALL_ARC - END_ARC) / 2);
        }
        if (angle < ((ALL_ARC - END_ARC) / 2)) {
            angle = ((ALL_ARC - END_ARC) / 2);
        }

        postionAngle(angle);
        getPoint(angle, 1);
    }

    // get Point and set Thumb to ARC
    private void getPoint(double thumbradius, int i) {
        if (i == 0) {
            thumbradius = thumbradius + ((ALL_ARC - END_ARC) / 2);
        }
        float xy[] = goQuadrant(thumbradius);
        if (thumbradius >= 0 && thumbradius < 90) {
            mThumbX = (float) mCpoint.x - Math.abs(xy[0]);
            mThumbY = (float) mCpoint.y + Math.abs(xy[1]);
        }
        if (thumbradius >= 90 && thumbradius < 180) {
            mThumbX = (float) mCpoint.x - Math.abs(xy[0]);
            mThumbY = (float) mCpoint.y - Math.abs(xy[1]);
        }
        if (thumbradius >= 180 && thumbradius < 270) {
            mThumbX = (float) mCpoint.x + Math.abs(xy[0]);
            mThumbY = (float) mCpoint.y - Math.abs(xy[1]);
        }
        if (thumbradius >= 270 && thumbradius < 360) {
            mThumbX = (float) mCpoint.x + Math.abs(xy[0]);
            mThumbY = (float) mCpoint.y + Math.abs(xy[1]);
        }
        invalidate();
    }

    // get postion
    private void postionAngle(double angle) {
        double mangle = angle - ((ALL_ARC - END_ARC) / 2);
        double proportion = (double) (END_ARC / 100f);
        mDotPosition = (float) (mangle / proportion);
        if (listener != null) {
            listener.onMove(mDotPosition);
        }
    }

    // Get the angle between two points
    private double buildingradius(double x, double y) {
        double temx = Math.abs(x - mCpoint.x);
        double temy = Math.abs(y - mCpoint.y);
        double angle = Math.atan2(temx, temy) * 180 / Math.PI;
        switch (getQuadrant(x, y)) {
            case 2:
                angle = 180 - angle;
                break;
            case 3:
                return angle;
            case 1:
                angle = angle + 180;
                break;
            case 4:
                angle = 360 - angle;
                break;
        }
        return angle;
    }

    // getPoint Quadrant
    private int getQuadrant(double x, double y) {
        if (mCpoint.x - x >= 0 && mCpoint.y - y >= 0) {
            return 2;
        }
        if (mCpoint.x - x <= 0 && mCpoint.y - y >= 0) {
            return 1;
        }
        if (mCpoint.x - x >= 0 && mCpoint.y - y <= 0) {
            return 3;
        }
        if (mCpoint.x - x <= 0 && mCpoint.y - y <= 0) {
            return 4;
        }
        return 0;
    }

    // This method can add something
    private boolean isTouchThumb(float x, float y) {
        if (inside(x, y)) {
            return true;
        }
        return false;
    }

    // if you touch the Thumb can return true else return false
    private boolean inside(float x, float y) {
        Rect r = new Rect((int) (mThumbX - mDotRadius),
                (int) (mThumbY - mDotRadius), (int) (mThumbX + mDotRadius),
                (int) (mThumbY + mDotRadius));
        if (r.contains((int) x, (int) y)) {
            return true;
        }
        return false;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mCpoint.x, mCpoint.y, mCircleradius, drawCircle);
        canvas.drawArc(rectF, START_ARC, END_ARC, false, drawArc);
        canvas.save();
        if (isNeedShaper) {
            LinearGradient gradient = new LinearGradient(xy[0], xy[1], mThumbX,
                    mThumbY, fromColor, toColor, TileMode.MIRROR);
            drawArcShaper.setShader(gradient);
            // canvas.drawArc(rectF, (float) angle - 45, END_ARC, false,
            // drawArc);
            if (angle != 0) {
                canvas.drawArc(rectF, START_ARC, (float) angle - 45, false,
                        drawArcShaper);
            } else {
                canvas.drawArc(rectF, START_ARC, (float) angle, false,
                        drawArcShaper);
            }
        }
        canvas.drawCircle(mThumbX, mThumbY, mDotRadius, drawDot);
        canvas.restore();
        super.onDraw(canvas);
    }

    /**
     *
     * CenterPoint
     *
     * @author jay
     */
    public static class CenterPoint {
        int x;
        int y;
        int w;
        int h;
    }

    public interface OnSeekMoveListener {
        public void onMove(float f);
    }

    private OnSeekMoveListener listener;

    /**
     * setOnSeekMoveListener
     *
     * @param listener
     */
    public void setOnSeekMoveListener(OnSeekMoveListener listener) {
        this.listener = listener;
    }

}