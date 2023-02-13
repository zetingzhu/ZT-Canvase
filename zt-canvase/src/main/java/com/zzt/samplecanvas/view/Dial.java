package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
/* 表盘 */
public class Dial extends View {
    private static final int HOUR_LINE_HEIGHT = 35;
    private static final int MINUTE_LINE_HEIGHT = 25;
    private Paint mCirclePaint, mLinePaint;
    private DrawFilter mDrawFilter;
    //圆心（表盘中心）
    private int mCenterX, mCenterY, mCenterRadius;

    // 圆环线宽度
    private int mCircleLineWidth;
    // 直线刻度线宽度
    private int mHourLineWidth, mMinuteLineWidth;
    // 时针长度
    private int mHourLineHeight;
    // 分针长度
    private int mMinuteLineHeight;
    // 刻度线的左、上位置
    private int mLineLeft, mLineTop;

    // 刻度线的下边位置
    private int mLineBottom;
    // 用于控制刻度线位置
    private int mFixLineHeight;

    public Dial(Context context) {
        this(context, null);
    }

    public Dial(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Dial(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);

        mCircleLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,
                getResources().getDisplayMetrics());
        mHourLineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        mMinuteLineWidth = mHourLineWidth / 2;

        mFixLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());

        mHourLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                HOUR_LINE_HEIGHT,
                getResources().getDisplayMetrics());
        mMinuteLineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                MINUTE_LINE_HEIGHT,
                getResources().getDisplayMetrics());
        initPaint();
    }

    private void initPaint() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mCircleLineWidth);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setStrokeWidth(mHourLineWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
        // 绘制表盘
        drawCircle(canvas);
        // 绘制刻度
        drawLines(canvas);
    }

    /* 绘制刻度 */
    private void drawLines(Canvas canvas) {
        for (int degree = 0; degree <= 360; degree++) {
            if (degree % 30 == 0) {
                //时针
                mLineBottom = mLineTop + mHourLineHeight;
                mLinePaint.setStrokeWidth(mHourLineWidth);
            } else {
                mLineBottom = mLineTop + mMinuteLineHeight;
                mLinePaint.setStrokeWidth(mMinuteLineWidth);
            }

            if (degree % 6 == 0) {
                canvas.save();
                canvas.rotate(degree, mCenterX, mCenterY);
                canvas.drawLine(mLineLeft, mLineTop, mLineLeft, mLineBottom, mLinePaint);
                canvas.restore();
            }
        }
    }

    /* 绘制表盘 */
    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mCenterRadius, mCirclePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        mCenterRadius = Math.min(mCenterX, mCenterY) - mCircleLineWidth / 2;

        mLineLeft = mCenterX - mMinuteLineWidth / 2;
        mLineTop = mCenterY - mCenterRadius;
    }
}