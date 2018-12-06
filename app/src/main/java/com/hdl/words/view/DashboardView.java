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
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.hdl.words.R;

/**
 * Date 2018/12/4 9:56
 * author HDL
 * Mail 229101253@qq.com
 */

public class DashboardView extends View {
    private Context context;
    private int outerRadius; //外环半径m
    private int innerRadius;//内环半径
    private int barWidth;//圆环宽度
    private int borderWidth;//外环距离view宽度
    private int startAngle ; // 起始角度   请注意图形不要超过Y轴右半轴
    private int sweepAngle ; // 绘制角度
    private int min ; // 最小值
    private int max ; // 最大值
    private int section ; // 值域（max-min）等分份数
    private int portion ; // 一个section等分份数
    private int realValue = min; // 实时读数
    private boolean isShowValue = true; // 是否显示实时读数
    private int strokeWidth ; // 画笔宽度
    private int scaleHeight ; // 长刻度的相对圆弧的长度
    private int scaleTextHeight ; // 刻度读数顶部的相对圆弧的长度
    private int pointSize ;//白色指针大小


    float[] p1,p2,p3;
    private int mPadding;
    private float mCenterX, mCenterY; // 圆心坐标
    private Paint paint;
    private Paint outerPaint;//外圆弧画笔
    private Paint innerPaint;//内圆弧画笔
    private Paint barPaint;//圆环画笔
    private Paint pointPaint;//指针画笔
    private Paint scalePaint;//刻度 包括点和线画笔

    private int backgroundColor;//view背景颜色，内环默认颜色一致
    private int barNormalColor;//圆弧未填充时的颜色
    private int barSelectColor ;//圆环填充时的颜色
    private int pointColor ;//指针颜色 默认白色
    private int scaleNormalColor ;//刻度未填充时颜色
    private int scaleSelectColor ;//刻度填充时的颜色
    private int realValueColor ;//实时读数颜色
    private int realValueNoteColor ;//实时读数提示颜色
    private int barLineColor ;//实时读书提示颜色

    private int sectionPointSize;//N等分圆点大小
    private int portionPointSize;//每一小份的圆点大小
    private int sectionTextSize;//N等分的Text大小
    private int barLineWidth;//圆环内部线的宽度
    private int barLineLength ;//圆环内部线的长度

    private RectF outerRectF;//外环
    private RectF innerRectF;//内环
    private RectF barRectF;//圆环
    private RectF sectionTextRectF;//长刻度读数
    private Path path;//字体环绕路径

    private Rect rectText;
    private String[] texts;
    private OnProgressChangeListener onProgressChangeListener;




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
        init();
        initAttributes(context,attrs);
        initTextData();
    }

    private void init() {
        strokeWidth = 5; // 画笔宽度
        scaleHeight = 10 + strokeWidth;
        scaleTextHeight = scaleHeight + 1;
        pointSize = 25;
        barWidth=50;//圆环宽度
        borderWidth=150;//外环距离view宽度
        startAngle = 300; // 起始角度
        sweepAngle = 120; // 绘制角度
        min = 50; // 最小值
        max = 100; // 最大值
        section = 5; // 值域（max-min）等分份数
        portion = 10; // 一个section等分份数
        realValue = min; // 实时读数默认为最小

        backgroundColor = 0xFF000000;//背景颜色
        barNormalColor = 0xFF888888;//外圆弧颜色
        barSelectColor = 0xff33b5e5;//圆环颜色
        pointColor = 0xFFFFFFFF;//指针颜色 默认白色
        scaleNormalColor = 0xFF888888;//刻度普通颜色
        scaleSelectColor = 0xFFFFFFFF;//刻度选中颜色
        realValueColor = 0xFFFFFFFF;//实时读数颜色
        realValueNoteColor = 0xFF888888;//实时读书提示颜色
        barLineColor = 0xFFFFFFFF;//圆环内部线的颜色

        sectionPointSize = 10;//N等分圆点大小
        portionPointSize = 5;//每一小份的圆点大小
        sectionTextSize = 2;//N等分的Text大小
        barLineWidth = 3;//圆环内部线的宽度
        barLineLength = 10;  //圆环内部线的宽度
        
        paint = new Paint();
        //外圆弧画笔
        outerPaint = new Paint();
        outerPaint.setStrokeWidth(strokeWidth);
        outerPaint.setAntiAlias(true);
        outerPaint.setColor(barNormalColor);
        //圆环画笔
        barPaint =new Paint();
        barPaint.setColor(barSelectColor);
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //内圆弧画笔
        innerPaint =new Paint();
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerPaint.setStrokeWidth(strokeWidth);
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(backgroundColor);
        //指针画笔
        pointPaint =new Paint();
        //刻度 包括点和线画笔
        scalePaint = new Paint();

        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);

        outerRectF = new RectF();
        innerRectF = new RectF();
        barRectF = new RectF();
        sectionTextRectF = new RectF();
        path = new Path();
        rectText = new Rect();

    }
    private void initAttributes(Context context,AttributeSet attrs){
        @SuppressLint("Recycle")
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.DashboardView);
        barWidth=typedArray.getInteger(R.styleable.DashboardView_bar_width,barWidth);
        startAngle=typedArray.getInteger(R.styleable.DashboardView_start_angle,startAngle);
        sweepAngle=typedArray.getInteger(R.styleable.DashboardView_sweep_angle,sweepAngle);
        min=typedArray.getInteger(R.styleable.DashboardView_min,min);
        max=typedArray.getInteger(R.styleable.DashboardView_max,max);
        section=typedArray.getInteger(R.styleable.DashboardView_section,section);
        portion=typedArray.getInteger(R.styleable.DashboardView_portion,portion);
        realValue=typedArray.getInteger(R.styleable.DashboardView_real_Value,realValue);
        pointSize=typedArray.getInteger(R.styleable.DashboardView_point_size,pointSize);

        backgroundColor=typedArray.getColor(R.styleable.DashboardView_view_background,backgroundColor);
        barNormalColor=typedArray.getColor(R.styleable.DashboardView_bar_normal_color,barNormalColor);
        barSelectColor=typedArray.getColor(R.styleable.DashboardView_bar_select_color,barSelectColor);
        pointColor=typedArray.getColor(R.styleable.DashboardView_point_color,pointColor);
        scaleNormalColor=typedArray.getColor(R.styleable.DashboardView_scale_normal_color,scaleNormalColor);
        scaleSelectColor=typedArray.getColor(R.styleable.DashboardView_scale_normal_color,scaleSelectColor);
        realValueColor=typedArray.getColor(R.styleable.DashboardView_real_value_color,realValueColor);
        realValueNoteColor=typedArray.getColor(R.styleable.DashboardView_real_value_note_color,realValueNoteColor);
        barLineColor=typedArray.getColor(R.styleable.DashboardView_bar_line_color,barLineColor);
        typedArray.recycle();
    }
    private void initTextData(){
        texts = new String[section + 1]; // 需要显示section + 1个刻度读数
        for (int i = 0; i < texts.length; i++) {
            int n = (max - min) / section;
            texts[i] = String.valueOf(max - i * n);
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
        outerRadius = (width - mPadding * 2 - strokeWidth * 2) / 2-borderWidth;
        innerRadius=outerRadius-barWidth;
        paint.setTextSize(sp2px(16));
        if (isShowValue) { // 显示实时读数，View高度增加字体高度3倍
            paint.getTextBounds("0", 0, "0".length(), rectText);
        } else {
            paint.getTextBounds("0", 0, 0, rectText);
        }
        setMeasuredDimension(width, height);
        mCenterX = getMeasuredWidth() / 2f;
        mCenterY = getMeasuredHeight()/2f;
        //外环
        outerRectF.set(
                mCenterX-outerRadius-strokeWidth,
                mCenterY-outerRadius-strokeWidth,
                mCenterY+outerRadius+strokeWidth,
                mCenterY+outerRadius+strokeWidth
        );
        barRectF.set(
                mCenterX-outerRadius-strokeWidth,
                mCenterY-outerRadius-strokeWidth,
                mCenterY+outerRadius+strokeWidth,
                mCenterY+outerRadius+strokeWidth);
        //内环
        innerRectF.set(
                mCenterX-innerRadius-strokeWidth,
                mCenterY-innerRadius-strokeWidth,
                mCenterY+innerRadius+strokeWidth,
                mCenterY+innerRadius+strokeWidth
        );

        //长刻度读数
        sectionTextRectF.set(
                mCenterX-outerRadius-strokeWidth,
                mCenterY-outerRadius-strokeWidth,
                mCenterX+outerRadius+strokeWidth,
                mCenterY+outerRadius+strokeWidth
        );
        paint.setTextSize(sp2px(10));
        paint.getTextBounds("0", 0, "0".length(), rectText);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);
        /**
         * 画外圆弧
         */
        canvas.drawArc(outerRectF, startAngle, sweepAngle, true, outerPaint);
        /**
         * 画圆环
         */
        canvas.drawArc(barRectF, startAngle+sweepAngle,
                -(sweepAngle*(realValue-min)/(max-min)),
                true, barPaint);
        /**
         * 画内圆弧
         */
        canvas.drawArc(innerRectF, startAngle, sweepAngle, true, innerPaint);
        /**
         * 画长刻度
         * 画好起始角度的一条刻度后通过canvas绕着原点旋转来画剩下的长刻度
         * 目前长刻度和短刻度都跟角度适配
         */
        drawSectionScale(canvas);

        /**
         * 画短刻度
         * 同样采用canvas的旋转原理
         */
        drawPortionScale(canvas);

        /**
         * 画圆环内部白线
         * 同样采用canvas的旋转原理
         */
        drawBarLineScale(canvas);
        /**
         * 画大刻度读数
         * 添加一个圆弧path，文字沿着path绘制
         * 目前刻度读数是跟着圆环走的
         */
        drawSectionScaleText(canvas);
        /**
         * 画指针
         * 指针也实现跟着圆环走!
         */
        drawPoint(canvas);
        /**
         * 画实时度数值
         */
        drawRealValue(canvas);
        invalidate();
    }
    private void drawSectionScale(Canvas canvas){
        scalePaint.setTextAlign(Paint.Align.CENTER);
        scalePaint.setStyle(Paint.Style.FILL);
        scalePaint.setStrokeWidth(sectionPointSize);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        double sin = Math.sin(Math.toRadians(startAngle));
        double cos = Math.cos(Math.toRadians(startAngle));
        float x1 = (float) (mCenterX+mPadding +  (outerRadius + scaleHeight) * cos);
        float y1 = (float) (mCenterY+mPadding +  (outerRadius + scaleHeight) * sin);
        canvas.save();
        canvas.rotate(sweepAngle,mCenterX,mCenterY);
        float angle = sweepAngle * 1f / (section) ;
        for (int i = 0; i <=section; i++) {
            if(i*(max-min)/section <= (realValue-min)){
                scalePaint.setColor(scaleSelectColor);
            }else{
                scalePaint.setColor(scaleNormalColor);
            }
            canvas.drawPoint(x1, y1, scalePaint);
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();
    }
    private void drawPortionScale(Canvas canvas){
        canvas.save();
        double sin = Math.sin(Math.toRadians(startAngle));
        double cos = Math.cos(Math.toRadians(startAngle));
        scalePaint.setColor(Color.GRAY);
        scalePaint.setStrokeWidth(portionPointSize);
        scalePaint.setStrokeCap(Paint.Cap.ROUND);
        float x3 = (float) (mCenterX + mPadding + (outerRadius + scaleHeight ) * cos);
        float y3 = (float) (mCenterY + mPadding + (outerRadius + scaleHeight ) * sin);
        canvas.rotate(sweepAngle,mCenterX,mCenterY);
        float angle = sweepAngle * 1f / (section * portion) ;
        for (int i = 0; i <= section * portion; i++) {
            if (i % portion == 0) { // 避免与长刻度画重合
                canvas.rotate(-angle, mCenterX, mCenterY);
                continue;
            }
            if(i*(max-min)/(portion*section)<= (realValue-min)){
                scalePaint.setColor(scaleSelectColor);
            }else{
                scalePaint.setColor(scaleNormalColor);
            }
            canvas.drawPoint(x3,y3,scalePaint);
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();
    }
    private void drawBarLineScale(Canvas canvas){
        canvas.save();
        //圆环内部白点
        double sin = Math.sin(Math.toRadians(startAngle));
        double cos = Math.cos(Math.toRadians(startAngle));
        float x2 = (float) (mCenterX+mPadding   + outerRadius  * cos);
        float y2 = (float) (mCenterY+mPadding   + outerRadius  * sin);
        float x4 = (float) (mCenterX+mPadding   + (outerRadius - barLineLength)  * cos);
        float y4 = (float) (mCenterY+mPadding   + (outerRadius - barLineLength)  * sin);
        float angle = sweepAngle * 1f / (section * portion) ;
        //先旋转至底部
        canvas.rotate(sweepAngle,mCenterX,mCenterY);
        scalePaint.setColor(barLineColor);
        scalePaint.setStrokeWidth(barLineWidth);
        for (int i = 0;
             i <= section * portion && i*(max-min)/(portion*section)<= (realValue-min);
             i++) {
            canvas.drawLine(x2,y2,x4,y4,scalePaint);
            //逆向画线或者点
            canvas.rotate(-angle, mCenterX, mCenterY);
        }
        canvas.restore();
    }
    private void drawSectionScaleText(Canvas canvas){
        scalePaint.setTextSize(sp2px(15));
        scalePaint.setStrokeWidth(sectionTextSize);
        scalePaint.setTextAlign(Paint.Align.LEFT);
        scalePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        scalePaint.setStrokeCap(Paint.Cap.BUTT);
        for (int i = 0; i < texts.length; i++) {
            paint.getTextBounds(texts[i], 0, texts[i].length(), rectText);
            // 粗略把文字的宽度视为圆心角2*θ对应的弧长，利用弧长公式得到θ，下面用于修正角度
            float θ = (float) (180 * rectText.width() / 2 /
                    (Math.PI * (outerRadius - scaleTextHeight - rectText.height())));
            path.reset();
            path.addArc(
                    sectionTextRectF,
                    startAngle + i * (sweepAngle / section) - θ, // 正起始角度减去θ使文字居中对准长刻度
                    sweepAngle
            );
            if(i*(max-min)/section == (max-realValue)){
                scalePaint.setColor(scaleSelectColor);
            }else{
                scalePaint.setColor(scaleNormalColor);
            }
            canvas.drawTextOnPath(texts[i], path, rectText.width()/2, -rectText.height()/2, scalePaint);
        }
    }
    private void drawPoint(Canvas canvas){
        pointPaint.setColor(pointColor);//指针颜色为白色
        float θ = startAngle + sweepAngle * (float)(max-realValue) / (max - min); // 指针与水平线夹角
        θ=θ%360;
        path.reset();
        float centerX=0,centerY=0;
        //实现新的坐标,让图标绕圆圈X下半部分
//        if(θ>=0){
//            centerX=(float) Math.abs((innerRadius-1.5*pointSize)*Math.sin((90.0-θ)/180*Math.PI));
//            centerY=(float)((innerRadius-1.5*pointSize)*Math.sin(θ/180*Math.PI));
//            //X上半部分
//        }else{
//            centerX=(float) Math.abs((innerRadius-1.5*pointSize)*Math.sin((θ+90.0)/180*Math.PI));
//            centerY=(float)((innerRadius-1.5*pointSize)*Math.sin(θ/180*Math.PI));
//        }
        p1 = getCoordinatePoint(pointSize,θ - 120);
        path.moveTo(centerX+p1[0],centerY+p1[1]);
        p2 = getCoordinatePoint(pointSize, θ);
        path.lineTo(centerX+p2[0],centerY+p2[1]);
        p3 = getCoordinatePoint(pointSize, θ + 120);
        path.lineTo(centerX+p3[0], centerY+p3[1]);
        path.close();
        canvas.drawPath(path, pointPaint);
    }
    private void drawRealValue(Canvas canvas){
        if (isShowValue) {
            paint.setTextSize(sp2px(16));
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setColor(realValueNoteColor);
            String value = String.valueOf(realValue);
            paint.getTextBounds(value, 0, value.length(), rectText);
            canvas.drawText("最大高度", mCenterX, mCenterY-rectText.height(), paint);
            paint.setColor(realValueColor);
            paint.setTextSize(sp2px(30));
            canvas.drawText(value+"%", mCenterX, mCenterY+rectText.height()*2, paint);
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
    //注意更改角度问题
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
    public int getrealValue() {
        return realValue;
    }

    public void setrealValue(int realValue) {
        if (this.realValue == realValue || realValue < min || realValue > max) {
            return;
        }
        this.realValue = realValue;
        postInvalidate();
    }
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener){
        this.onProgressChangeListener=onProgressChangeListener;
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
//        if(distanceX<0)
//            return;
        float distanceY=y-mCenterY;
        float distance = (float) Math.sqrt(Math.pow((distanceX), 2)
                + Math.pow((distanceY), 2));
        //右半边角度适配已解决!
        if(distance<=outerRadius&&distance>=innerRadius-pointSize*2.5){
            float angle=(float)(Math.atan((distanceY/distanceX))/Math.PI*180);
            Log.e("sdad111",angle+"     ");
            if(angle<=(startAngle+sweepAngle)%360&&angle>=-(360-startAngle)){
                if(distanceX>=0){
                    angle=angle%360;//将角度转换为正值
                    //将角度转换为value值
                    realValue=Math.round((((startAngle+sweepAngle)%360-angle)/sweepAngle)*(max-min)+min);
                    onProgressChangeListener.onProgressChange(this,realValue);
                    invalidate();
                }else{
                    angle=angle%360+180;//将角度转换为正值
                    //将角度转换为value值
                    realValue=Math.round((((startAngle+sweepAngle)%360-angle)/sweepAngle)*(max-min)+min);
                    onProgressChangeListener.onProgressChange(this,realValue);
                    invalidate();
                }

            }
        }
    }
    public interface OnProgressChangeListener{
        void onProgressChange(DashboardView view, float progress);
    }
    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public void setMin(int min) {
        this.min = min;
        if(realValue<min){
            realValue=min;
        }
        initTextData();
    }

    public void setMax(int max) {
        this.max = max;
        initTextData();
    }

    public void setSection(int section) {
        this.section = section;
        initTextData();
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public void setRealValue(int realValue) {
        this.realValue = realValue;
    }

    public void setShowValue(boolean showValue) {
        isShowValue = showValue;
    }

    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBarNormalColor(int barNormalColor) {
        this.barNormalColor = barNormalColor;
    }

    public void setBarSelectColor(int barSelectColor) {
        this.barSelectColor = barSelectColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public void setScaleNormalColor(int scaleNormalColor) {
        this.scaleNormalColor = scaleNormalColor;
    }

    public void setScaleSelectColor(int scaleSelectColor) {
        this.scaleSelectColor = scaleSelectColor;
    }

    public void setRealValueColor(int realValueColor) {
        this.realValueColor = realValueColor;
    }

    public void setRealValueNoteColor(int realValueNoteColor) {
        this.realValueNoteColor = realValueNoteColor;
    }

    public void setBarLineColor(int barLineColor) {
        this.barLineColor = barLineColor;
    }
}
