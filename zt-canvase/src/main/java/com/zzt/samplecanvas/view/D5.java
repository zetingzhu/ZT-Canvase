package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
public class D5 extends View {
    private static final String TAG = D5.class.getSimpleName();
    private boolean touchEnable = false;
    Paint paint;

    int drawX = 0;
    int drawY = 0;

    public int getDrawX() {
        return drawX;
    }

    public int getDrawY() {
        return drawY;
    }

    public D5(Context context) {
        super(context);
        initView();
    }

    public D5(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public D5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔

        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "D5 测量结果 H:" + getMeasuredHeight() + " W:" + getMeasuredWidth());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent  event:" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEnable = true;
                break;
            case MotionEvent.ACTION_UP:
                if (touchEnable) {
                    invalidate();
                }
                break;
        }
        return touchEnable;
    }

    public void setTouchView(boolean touch) {
        touchEnable = touch;
    }


    Canvas drawCanvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawCanvas = canvas;
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, 5, 5, paint);

        if (touchEnable) {
            onDrawDown(canvas);
        } else {
            onDrawUp(canvas);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.w(TAG, "onAttachedToWindow");
    }

    private void onDrawUp(Canvas canvas) {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        paint.setColor(color);


        Log.w(TAG, "查看一下自己测量结果" + getMeasuredHeight() + " get:" + getWidth());

        drawX = 200;
        drawY = 200;
        canvas.drawCircle(drawX / 2, drawY / 2, 90, paint);
    }

    private void onDrawDown(Canvas canvas) {
        paint.setColor(Color.BLUE);
        drawX = 200;
        drawY = 100;
        RectF rect = new RectF(0, 0, drawX, drawY);
        canvas.drawRect(rect, paint);
    }
}
