package com.hdl.words.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.hdl.words.R;

/**
 * DashboardView style 1
 * Created by woxingxiao on 2016-11-19.
 */

public class CarDoorAngleDashboard extends View {
    private Context context;
    private int outerRadius; //外圆环半径
    private int innerRadius;//内半圆半径
    private int barWidth=50;//圆环宽度
    private int borderWidth=200;//外环距离view宽度
    private int mStartAngle = 300; // 起始角度
    private int mSweepAngle = 120; // 绘制角度
    private int mMin = 50; // 最小值
    private int mMax = 100; // 最大值
    private int mSection = 5; // 值域（mMax-mMin）等分份数
    private int mPortion = 10; // 一个mSection等分份数
    private int mTriangleLength=dp2px(6);//白色指针大小
    private String mHeaderText  ; // 表头
    private int mRealTimeValue = mMin; // 实时读数
    private boolean isShowValue = true; // 是否显示实时读数
    private int mStrokeWidth; // 画笔宽度
    private int mLength1; // 长刻度的相对圆弧的长度
    private int mLength2; // 刻度读数顶部的相对圆弧的长度

    float[] p1,p2,p3;
    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private Paint mOuterPaint;//外圆弧颜色
    private Paint mInnerPaint;//内圆弧颜色
    private Paint mBarPaint;//圆环画笔
    private Paint mPointPaint;//指针颜色
    private Paint mLongScalePaint;//长刻度颜色
    private RectF mOuterRectF;//外环  因为方向是逆向的,所以圆环颜色实际是外环的颜色
    private RectF mInnerRectF;//内环
    private RectF mBarRectF;//圆环颜色
    private RectF mLongScaleRectF;//长刻度读数
    private Path mPath;

    private Rect mRectText;
    private String[] mTexts;

    public CarDoorAngleDashboard(Context context) {
        this(context, null);
        this.context=context;
    }

    public CarDoorAngleDashboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }

    public CarDoorAngleDashboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        mStrokeWidth = dp2px(1);
        mLength1 = dp2px(4) + mStrokeWidth;
        mLength2 = mLength1 + dp2px(1);
        //mPSRadius = dp2px(10);

        mPaint = new Paint();
        mOuterPaint = new Paint();
        mBarPaint =new Paint();
        mInnerPaint =new Paint();
        mPointPaint =new Paint();
        mLongScalePaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mOuterRectF = new RectF();
        mInnerRectF = new RectF();
        mBarRectF = new RectF();
        mLongScaleRectF = new RectF();
        mPath = new Path();
        mRectText = new Rect();

        mTexts = new String[mSection + 1]; // 需要显示mSection + 1个刻度读数
        for (int i = 0; i < mTexts.length; i++) {
            int n = (mMax - mMin) / mSection;
            mTexts[i] = String.valueOf(mMax - i * n);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPadding = Math.max(
                Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom())
        );
        setPadding(mPadding, mPadding, mPadding, mPadding);
        int width = resolveSize(dp2px(200), widthMeasureSpec);
        int height = resolveSize(dp2px(200), heightMeasureSpec);
        outerRadius = (width - mPadding * 2 - mStrokeWidth * 2) / 2-borderWidth;
        innerRadius=outerRadius-barWidth;
        mPaint.setTextSize(sp2px(16));
        if (isShowValue) { // 显示实时读数，View高度增加字体高度3倍
            mPaint.getTextBounds("0", 0, "0".length(), mRectText);
        } else {
            mPaint.getTextBounds("0", 0, 0, mRectText);
        }
        setMeasuredDimension(width, height);
        mCenterX = mCenterY = getMeasuredWidth() / 2f;
        //外环
        mOuterRectF.set(
                mCenterX-outerRadius-mStrokeWidth,
                mCenterY-outerRadius-mStrokeWidth,
                mCenterY+outerRadius+mStrokeWidth,
                mCenterY+outerRadius+mStrokeWidth
        );
        mBarRectF.set(
                mCenterX-outerRadius-mStrokeWidth,
                mCenterY-outerRadius-mStrokeWidth,
                mCenterY+outerRadius+mStrokeWidth,
                mCenterY+outerRadius+mStrokeWidth);
        //内环
        mInnerRectF.set(
                mCenterX-innerRadius-mStrokeWidth,
                mCenterY-innerRadius-mStrokeWidth,
                mCenterY+innerRadius+mStrokeWidth,
                mCenterY+innerRadius+mStrokeWidth
        );
        //长刻度
        mLongScaleRectF.set(
                mCenterX-outerRadius-mStrokeWidth-mLength2,//    getPaddingLeft() + mLength2 + mRectText.height(),
                mCenterY-outerRadius-mStrokeWidth-mLength2,//outerRadiusgetPaddingTop() + mLength2 + mRectText.height(),
                mCenterX+outerRadius+mStrokeWidth+mLength2,//outerRadiusgetMeasuredWidth() - getPaddingRight() - mLength2 - mRectText.height(),
                mCenterY+outerRadius+mStrokeWidth+mLength2//outerRadiusgetMeasuredWidth() - getPaddingBottom() - mLength2 - mRectText.height()
        );
        mPaint.setTextSize(sp2px(10));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);


        //mPLRadius = outerRadius - (mLength2 + mRectText.height() + dp2px(5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        /**
         * 画外圆弧
         */
        mOuterPaint.setStrokeWidth(mStrokeWidth);
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(Color.parseColor("#ff33b5e5"));
        canvas.drawArc(mOuterRectF, mStartAngle, mSweepAngle, true, mOuterPaint);
        /**
         * 画圆环
         */
        mBarPaint.setColor(Color.GRAY);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawArc(mBarRectF, mStartAngle,
                -(180-mStartAngle + mSweepAngle * (mRealTimeValue - mMin) / (mMax - mMin)),
                true, mBarPaint);
        /**
         * 画内圆弧
         */
        mInnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mInnerPaint.setStrokeWidth(mStrokeWidth);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.WHITE);
        canvas.drawArc(mInnerRectF, mStartAngle, mSweepAngle, true, mInnerPaint);
        canvas.save();
        /**
         * 画长刻度
         * 画好起始角度的一条刻度后通过canvas绕着原点旋转来画剩下的长刻度
         */
//        double cos = Math.cos(Math.toRadians(mStartAngle - 330));
//        double sin = Math.sin(Math.toRadians(mStartAngle - 330));
//        float x0 = (float) (outerRadius);
//        float y0 = (float) (outerRadius);
//        float x1 = (float) (outerRadius - (outerRadius - mLength1) * sin);
//        float y1 = (float) (outerRadius - (outerRadius - mLength1) * cos);
//        canvas.save();
//        canvas.drawLine(x0, y0, x1, y1, mPaint);
//        float angle = mSweepAngle * 1f / mSection ;
//        for (int i = 0; i < mSection; i++) {
//            canvas.rotate(angle, mCenterX, mCenterY);
//            canvas.drawLine(x0, y0, x1, y1, mPaint);
//        }


//        /**
//         * 画短刻度
//         * 同样采用canvas的旋转原理
//         */
//        canvas.save();
//        mPaint.setStrokeWidth(2);
//        //mPaint.setStrokeCap(Paint.Cap.ROUND);
//        float x2 = (float) (mPadding + mStrokeWidth + outerRadius + (outerRadius - mLength1 / 2f) * cos);
//        float y2 = (float) (mPadding + mStrokeWidth + outerRadius + (outerRadius - mLength1 / 2f) * sin);
//        //canvas.drawLine(x0, y0, x2, y2, mPaint);
//        angle = mSweepAngle * 1f / (mSection * mPortion) ;
//        for (int i = 1; i < mSection * mPortion; i++) {
//            canvas.rotate(angle, mCenterX, mCenterY);
//            if (i % mPortion == 0) { // 避免与长刻度画重合
//                continue;
//            }
//            canvas.drawCircle(x2,y2,dp2px(1),mPaint);
//        }
//        canvas.restore();




//        /**
//         * 画长刻度
//         * 画好起始角度的一条刻度后通过canvas绕着原点旋转来画剩下的长刻度
//         */
//        double cos = Math.cos(Math.toRadians(mStartAngle - 180));
//        double sin = Math.sin(Math.toRadians(mStartAngle - 180));
//        float x0 = (float) (mPadding + mStrokeWidth + outerRadius * (1 - cos));
//        float y0 = (float) (mPadding + mStrokeWidth + outerRadius * (1 - sin));
//        float x1 = (float) (mPadding + mStrokeWidth + outerRadius - (outerRadius - mLength1) * cos);
//        float y1 = (float) (mPadding + mStrokeWidth + outerRadius - (outerRadius - mLength1) * sin);
//        canvas.save();
//        canvas.drawLine(x0, y0, x1, y1, mPaint);
//        float angle = mSweepAngle * 1f / mSection ;
//        for (int i = 0; i < mSection; i++) {
//            canvas.rotate(angle, mCenterX, mCenterY);
//            canvas.drawLine(x0, y0, x1, y1, mPaint);
//        }
//        canvas.restore();
//        /**
//         * 画短刻度
//         * 同样采用canvas的旋转原理
//         */
////        canvas.save();
////        mPaint.setStrokeWidth(2);
////        mPaint.setStrokeCap(Paint.Cap.ROUND);
////        float x2 = (float) (mPadding + mStrokeWidth + outerRadius + (outerRadius - mLength1 / 2f) * cos);
////        float y2 = (float) (mPadding + mStrokeWidth + outerRadius + (outerRadius - mLength1 / 2f) * sin);
////        //canvas.drawLine(x0, y0, x2, y2, mPaint);
////        angle = mSweepAngle * 1f / (mSection * mPortion) ;
////        for (int i = 1; i < mSection * mPortion; i++) {
////            canvas.rotate(angle, mCenterX, mCenterY);
////            if (i % mPortion == 0) { // 避免与长刻度画重合
////                continue;
////            }
////            canvas.drawCircle(x2,y2,dp2px(1),mPaint);
////        }
////        canvas.restore();
        /**
         * 画长刻度读数
         * 添加一个圆弧path，文字沿着path绘制
         */
        mLongScalePaint.setTextSize(sp2px(10));
        mLongScalePaint.setTextAlign(Paint.Align.LEFT);
        mLongScalePaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mTexts.length; i++) {
            mPaint.getTextBounds(mTexts[i], 0, mTexts[i].length(), mRectText);
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * mRectText.width() / 2 /
                    (Math.PI * (outerRadius - mLength2 - mRectText.height())));
            mPath.reset();
            mPath.addArc(
                    mLongScaleRectF,
                    mStartAngle + i * (mSweepAngle / mSection) - θ, // 正起始角度减去θ使文字居中对准长刻度
                    mSweepAngle
            );
            canvas.drawTextOnPath(mTexts[i], mPath, 0, 0, mLongScalePaint);
        }

//        /**
//         * 画表头
//         * 没有表头就不画
//         */
//        if (!TextUtils.isEmpty(mHeaderText)) {
//            mPaint.setTextSize(sp2px(14));
//            mPaint.setTextAlign(Paint.Align.CENTER);
//            mPaint.getTextBounds(mHeaderText, 0, mHeaderText.length(), mRectText);
//            canvas.drawText(mHeaderText, mCenterX, mCenterY / 2f + mRectText.height(), mPaint);
//        }

        /**
         * 画指针
         */
        float θ = mStartAngle + mSweepAngle * (mRealTimeValue - mMin) / (mMax - mMin); // 指针与水平线夹角
        //让指针反向
        θ=360-θ;
        mPath.reset();
        float centerX,centerY;
        //实现新的坐标,让图标绕圆圈X下半部分
        if(θ>=0){
            centerX=(float) Math.abs((innerRadius-1.5*mTriangleLength)*Math.sin((90.0-θ)/180*Math.PI));
            centerY=(float)((innerRadius-1.5*mTriangleLength)*Math.sin(θ/180*Math.PI));
            //X上半部分
        }else{
            centerX=(float) Math.abs((innerRadius-1.5*mTriangleLength)*Math.sin((θ+90.0)/180*Math.PI));
            centerY=(float)((innerRadius-1.5*mTriangleLength)*Math.sin(θ/180*Math.PI));
        }
        p1 = getCoordinatePoint(mTriangleLength,θ - 120);
        mPath.moveTo(centerX+p1[0],centerY+p1[1]);
        p2 = getCoordinatePoint(mTriangleLength, θ);
        mPath.lineTo(centerX+p2[0],centerY+p2[1]);
        p3 = getCoordinatePoint(mTriangleLength, θ + 120);
        mPath.lineTo(centerX+p3[0], centerY+p3[1]);
        mPointPaint.setColor(Color.parseColor("#ff33b5e5"));//指针颜色为白色
        mPath.close();
        canvas.drawPath(mPath, mPointPaint);


//        /**
//         * 画指针围绕的镂空圆心
//         */
//        mPaint.setColor(Color.WHITE);
//        canvas.drawCircle(mCenterX, mCenterY, dp2px(2), mPaint);

        /**
         * 画实时度数值
         */
        if (isShowValue) {
            mPaint.setTextSize(sp2px(16));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            String value = String.valueOf(mRealTimeValue);
            mPaint.getTextBounds(value, 0, value.length(), mRectText);
            canvas.drawText("最大高度"+value+"%", mCenterX, mCenterY+mRectText.height()/2, mPaint);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

    public float[] getCoordinatePoint(int radius, float angle) {
        float[] point = new float[2];
        double arcAngle = Math.toRadians(angle); //将角度转换为弧度
        if (angle < 90) {
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 90) {
            point[0] = mCenterX;
            point[1] = mCenterY + radius;
        } else if (angle > 90 && angle < 180) {
            arcAngle = Math.PI * (180 - angle) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY + Math.sin(arcAngle) * radius);
        } else if (angle == 180) {
            point[0] = mCenterX - radius;
            point[1] = mCenterY;
        } else if (angle > 180 && angle < 270) {
            arcAngle = Math.PI * (angle - 180) / 180.0;
            point[0] = (float) (mCenterX - Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        } else if (angle == 270) {
            point[0] = mCenterX;
            point[1] = mCenterY - radius;
        } else {
            arcAngle = Math.PI * (360 - angle) / 180.0;
            point[0] = (float) (mCenterX + Math.cos(arcAngle) * radius);
            point[1] = (float) (mCenterY - Math.sin(arcAngle) * radius);
        }
        Log.e("getCoordinatePoint"," angle "+angle+" radius "+radius+" point[0] "+point[0]+" point[1] "+point[1]);
        return point;
    }
    public int getRealTimeValue() {
        return mRealTimeValue;
    }

    public void setRealTimeValue(int realTimeValue) {
        if (mRealTimeValue == realTimeValue || realTimeValue < mMin || realTimeValue > mMax) {
            return;
        }
        mRealTimeValue = realTimeValue;
        postInvalidate();
    }
    public float getmCenterX() {
        return mCenterX;
    }

    public float getmCenterY() {
        return mCenterY;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved(x,y);
            case MotionEvent.ACTION_MOVE:
                moved(x,y);
                break;
            case MotionEvent.ACTION_UP:
                moved(x,y);
                break;
        }
        return true;
    }
    private void moved(float x,float y) {
        float distanceX=x-mCenterX;
        if(distanceX<0)
            return;
        float distanceY=y-mCenterY;
        float distance = (float) Math.sqrt(Math.pow((distanceX), 2)
                + Math.pow((distanceY), 2));
        if(distance<=outerRadius&&distance>=innerRadius-mTriangleLength*2.5){
            float angle=(float)Math.atan( (distanceY/distanceX))*(mSweepAngle/2);
            if(angle<=60&&angle>=-(60)){
                angle=360-angle;
                mRealTimeValue=Math.round((angle-mStartAngle)*(mMax-mMin)/mSweepAngle+mMin);
                invalidate();
            }
        }

    }

}
