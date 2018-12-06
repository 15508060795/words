package com.hdl.words.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.hdl.words.R;

/**
 * Date 2018/12/4 9:56
 * auther HDL
 * Mail 229101253@qq.com
 */

public class DashboardView extends View {
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

    private int mRealTimeValue = mMin; // 实时读数
    private boolean isShowValue = true; // 是否显示实时读数
    private int mStrokeWidth =dp2px(1); // 画笔宽度
    private int mLength1 = dp2px(4) + mStrokeWidth; // 长刻度的相对圆弧的长度
    private int mLength2 = mLength1 + dp2px(1); // 刻度读数顶部的相对圆弧的长度
    private int mTriangleLength = dp2px(6);//白色指针大小

    float[] p1,p2,p3;
    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint mPaint;
    private Paint mOuterPaint;//外圆弧画笔
    private Paint mInnerPaint;//内圆弧画笔
    private Paint mBarPaint;//圆环画笔
    private Paint mPointPaint;//指针画笔
    private Paint mScalePaint;//长刻度数字画笔

    private int mOuterColor = 0xFF888888;//外圆弧颜色
    private int mBarColor = 0xff33b5e5;//圆环颜色
    private int mPointColor = 0xFFFFFFFF;//指针颜色 默认白色
    private int mScaleNormalColor = 0xFF888888;//刻度普通颜色
    private int mScaleSelectColor = 0xFFFFFFFF;//刻度选中颜色

    
    
    private RectF mOuterRectF;//外环
    private RectF mInnerRectF;//内环
    private RectF mBarRectF;//圆环
    private RectF mSectionTextRectF;//长刻度读数
    private Path mPath;

    private Rect mRectText;
    private String[] mTexts;
    private OnProgressChangeListener mOnProgressChangeListener;




    public DashboardView(Context context) {
        this(context, null);
        this.context=context;
    }

    public DashboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }

    public DashboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initAttributes(context,attrs);
        init();
        initData();
    }

    private void init() {
        mPaint = new Paint();

        mOuterPaint = new Paint();
        mOuterPaint.setStrokeWidth(mStrokeWidth);
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterColor);

        mBarPaint =new Paint();
        mBarPaint.setColor(mBarColor);
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        
        
        mInnerPaint =new Paint();
        mInnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mInnerPaint.setStrokeWidth(mStrokeWidth);
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(Color.BLACK);

        mPointPaint =new Paint();

        mScalePaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterRectF = new RectF();
        mInnerRectF = new RectF();
        mBarRectF = new RectF();
        mSectionTextRectF = new RectF();
        mPath = new Path();
        mRectText = new Rect();

    }
    private void initAttributes(Context context,AttributeSet attrs){
        @SuppressLint("Recycle")
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
        mMin=typedArray.getInteger(R.styleable.DashboardView_progress_min,mMin);
        mMax=typedArray.getInteger(R.styleable.DashboardView_progress_max,mMax);
        mSection=typedArray.getInteger(R.styleable.DashboardView_big_section,mSection);
        mPortion=typedArray.getInteger(R.styleable.DashboardView_small_portion,mPortion);
        barWidth=typedArray.getInteger(R.styleable.DashboardView_bar_width,barWidth);
        
        
        mBarColor=typedArray.getColor(R.styleable.DashboardView_bar_select_color,mBarColor);
        mOuterColor=typedArray.getColor(R.styleable.DashboardView_bar_normal_color,mOuterColor);
        mPointColor=typedArray.getColor(R.styleable.DashboardView_point_color,mPointColor);
        //长刻度字体颜色
        mScaleNormalColor=typedArray.getColor(R.styleable.DashboardView_scale_normal_color,mScaleNormalColor);
        mScaleSelectColor=typedArray.getColor(R.styleable.DashboardView_scale_normal_color,mScaleSelectColor);
        typedArray.recycle();
    }
    private void initData(){
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
        mCenterX = getMeasuredWidth() / 2f;
        mCenterY = getMeasuredHeight()/2f;
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

        //长刻度读数
        mSectionTextRectF.set(
                mCenterX-outerRadius-mStrokeWidth,
                mCenterY-outerRadius-mStrokeWidth,
                mCenterX+outerRadius+mStrokeWidth,
                mCenterY+outerRadius+mStrokeWidth
//                mCenterX-outerRadius-mStrokeWidth-mLength2/2f,
//                mCenterY-outerRadius-mStrokeWidth-mLength2/2f,
//                mCenterX+outerRadius+mStrokeWidth+mLength2/2f,
//                mCenterY+outerRadius+mStrokeWidth+mLength2/2f
        );
        mPaint.setTextSize(sp2px(10));
        mPaint.getTextBounds("0", 0, "0".length(), mRectText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        /**
         * 画外圆弧
         */

        canvas.drawArc(mOuterRectF, mStartAngle, mSweepAngle, true, mOuterPaint);
        /**
         * 画圆环
         */

        canvas.drawArc(mBarRectF, mStartAngle+mSweepAngle,
                -(mSweepAngle*(mRealTimeValue-mMin)/(mMax-mMin)),
                true, mBarPaint);
        /**
         * 画内圆弧
         */

        canvas.drawArc(mInnerRectF, mStartAngle, mSweepAngle, true, mInnerPaint);
        canvas.save();
        /**
         * 画长刻度
         * 画好起始角度的一条刻度后通过canvas绕着原点旋转来画剩下的长刻度
         * 目前长刻度和短刻度都跟角度适配
         */
        mScalePaint.setTextAlign(Paint.Align.CENTER);
        mScalePaint.setStyle(Paint.Style.FILL);
        mScalePaint.setStrokeWidth(10);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        double sin = Math.sin(Math.toRadians(mStartAngle));
        double cos = Math.cos(Math.toRadians(mStartAngle));
        float x1 = (float) (mCenterX+mPadding + mStrokeWidth +  (outerRadius + mLength1) * cos);
        float y1 = (float) (mCenterY+mPadding + mStrokeWidth +  (outerRadius + mLength1) * sin);
        canvas.save();
        canvas.rotate(mSweepAngle,mCenterX,mCenterY);
        float angle = mSweepAngle * 1f / (mSection) ;
        for (int i = 0; i <=mSection; i++) {
            if(i*(mMax-mMin)/mSection <= (mRealTimeValue-mMin)){
                mScalePaint.setColor(mScaleSelectColor);
            }else{
                mScalePaint.setColor(mScaleNormalColor);
            }
            canvas.drawPoint(x1, y1, mScalePaint);
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();

        /**
         * 画短刻度
         * 同样采用canvas的旋转原理
         */
        canvas.save();
        mScalePaint.setColor(Color.GRAY);
        mScalePaint.setStrokeWidth(5);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        float x3 = (float) (mCenterX+mPadding + mStrokeWidth  + (outerRadius + mLength1 ) * cos);
        float y3 = (float) (mCenterY+mPadding + mStrokeWidth  + (outerRadius + mLength1 ) * sin);
        canvas.rotate(mSweepAngle,mCenterX,mCenterY);
        angle = mSweepAngle * 1f / (mSection * mPortion) ;
        for (int i = 0; i <= mSection * mPortion; i++) {
            if (i % mPortion == 0) { // 避免与长刻度画重合
                canvas.rotate(-angle, mCenterX, mCenterY);
                continue;
            }
            if(i*(mMax-mMin)/(mPortion*mSection)<= (mRealTimeValue-mMin)){
                mScalePaint.setColor(mScaleSelectColor);
            }else{
                mScalePaint.setColor(mScaleNormalColor);
            }
            canvas.drawPoint(x3,y3,mScalePaint);
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();

        /**
         * 画圆环内部白线
         * 同样采用canvas的旋转原理
         */
        canvas.save();
        //圆环内部白点
        float x2 = (float) (mCenterX+mPadding + mStrokeWidth  + outerRadius  * cos);
        float y2 = (float) (mCenterY+mPadding + mStrokeWidth  + outerRadius  * sin);
        float x4 = (float) (mCenterX+mPadding + mStrokeWidth  + (outerRadius-10)  * cos);
        float y4 = (float) (mCenterY+mPadding + mStrokeWidth  + (outerRadius-10)  * sin);
        angle = mSweepAngle * 1f / (mSection * mPortion) ;
        //先旋转至底部
        canvas.rotate(mSweepAngle,mCenterX,mCenterY);
        mScalePaint.setColor(Color.WHITE);
        mScalePaint.setStrokeWidth(3);
        for (int i = 0;
             i <= mSection * mPortion && i*(mMax-mMin)/(mPortion*mSection)<= (mRealTimeValue-mMin);
             i++) {
            canvas.drawLine(x2,y2,x4,y4,mScalePaint);
            //逆向画线或者点
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();
        /**
         * 画长刻度读数
         * 添加一个圆弧path，文字沿着path绘制
         * 目前刻度读数是跟着圆环走的
         */
        mScalePaint.setTextSize(sp2px(15));
        mScalePaint.setStrokeWidth(2);
        mScalePaint.setTextAlign(Paint.Align.LEFT);
        mScalePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mScalePaint.setStrokeCap(Paint.Cap.BUTT);
        for (int i = 0; i < mTexts.length; i++) {
            mPaint.getTextBounds(mTexts[i], 0, mTexts[i].length(), mRectText);
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * mRectText.width() / 2 /
                    (Math.PI * (outerRadius - mLength2 - mRectText.height())));
            mPath.reset();
            mPath.addArc(
                    mSectionTextRectF,
                    mStartAngle + i * (mSweepAngle / mSection) - θ, // 正起始角度减去θ使文字居中对准长刻度
                    mSweepAngle
            );
            if(i*(mMax-mMin)/mSection == (mMax-mRealTimeValue)){
                mScalePaint.setColor(mScaleSelectColor);
            }else{
                mScalePaint.setColor(mScaleNormalColor);
            }
            canvas.drawTextOnPath(mTexts[i], mPath, mRectText.width()/2, -mRectText.height()/2, mScalePaint);
        }
        /**
         * 画指针
         * 指针也实现跟着圆环走!
         */
        mPointPaint.setColor(mPointColor);//指针颜色为白色
        float θ = mStartAngle + mSweepAngle * (float)(mMax-mRealTimeValue) / (mMax - mMin); // 指针与水平线夹角
        θ=θ%360;
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
        mPath.close();
        canvas.drawPath(mPath, mPointPaint);

        /**
         * 画实时度数值
         */
        if (isShowValue) {
            mPaint.setTextSize(sp2px(16));
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            String value = String.valueOf(mRealTimeValue);
            mPaint.getTextBounds(value, 0, value.length(), mRectText);
            canvas.drawText("最大高度", mCenterX, mCenterY-mRectText.height(), mPaint);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(sp2px(30));
            canvas.drawText(value+"%", mCenterX, mCenterY+mRectText.height()*2, mPaint);
        }
        invalidate();
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
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener){
        this.mOnProgressChangeListener=onProgressChangeListener;
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
                break;
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
        //右半边角度适配已解决!
        if(distance<=outerRadius&&distance>=innerRadius-mTriangleLength*2.5){
            float angle=(float)(Math.atan((distanceY/distanceX))/Math.PI*180);
            if(angle<=(mStartAngle+mSweepAngle)%360&&angle>=-(360-mStartAngle)){
                angle=angle%360+360;//将角度转换为正值
                //将角度转换为value值
                mRealTimeValue=Math.round(((mStartAngle+mSweepAngle-angle)/mSweepAngle)*(mMax-mMin)+mMin);
                mOnProgressChangeListener.onProgressChange(this,mRealTimeValue);
                invalidate();
            }
        }
    }
    public interface OnProgressChangeListener{
        void onProgressChange(DashboardView view, float progress);
    }
}
