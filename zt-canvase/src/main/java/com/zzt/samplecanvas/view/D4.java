package com.zzt.samplecanvas.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.zzt.samplecanvas.entiy.TouchViewRect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
public class D4 extends View {
    private static final String TAG = D4.class.getSimpleName();
    Context mContext;

    List<TouchViewRect> touchRectList;

    // 当前选中view
    D5 selectedView;

    public D4(Context context) {
        super(context);
        initView(context);
    }

    public D4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public D4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.w(TAG, "onTouchEvent 点击位置 X:" + event.getX() + " Y:" + event.getY());
                for (TouchViewRect touchViewRect : touchRectList) {
                    D5 touchView = touchViewRect.getTouchView();
                    touchViewRect.setTouchRect(new Rect(touchViewRect.getStartX(), touchViewRect.getStartY(), touchViewRect.getStartX() + touchView.getDrawX(), touchViewRect.getStartY() + touchView.getDrawY()));
                    Log.d(TAG, "onTouchEvent  测量 label: " + touchViewRect.getLabel() + " W:" + touchViewRect.getTouchRect());
                    if (findClickView(touchViewRect.getTouchRect(), (int) event.getX(), (int) event.getY())) {
                        Log.i(TAG, "这个点击事件有没有进入label: " + touchViewRect.getLabel());
                        if (selectedView != null) {
                            selectedView.setTouchView(false);
                        }
                        selectedView = touchViewRect.getTouchView();
                        selectedView.setTouchView(true);
                        invalidate();
                        break;
                    }
                }
                break;
        }
        return true;
    }


    public boolean findClickView(Rect rect, int x, int y) {
        if (rect != null) {
            if (rect.contains(x, y)) {
                return true;
            }
        }
        return false;
    }


    public void initView(Context context) {
        mContext = context;
        touchRectList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            D5 d5 = new D5(getContext());
            TouchViewRect touchViewRect = new TouchViewRect("label_" + i, d5);
            int randomX = (int) (Math.random() * 500);
            int randomY = (int) (Math.random() * 500);
            touchViewRect.setStartX(randomX);
            touchViewRect.setStartY(randomY);
            touchRectList.add(touchViewRect);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "D4 测量结果 H:" + getMeasuredHeight() + " W:" + getMeasuredWidth());
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < touchRectList.size(); i++) {
                    D5 d5 = touchRectList.get(i).getTouchView();
//            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            d5.measure(w, h);
                    Log.d(TAG, "onAttachedToWindow W:" + d5.getMeasuredWidth() + " H:" + d5.getMeasuredHeight());
                }
            }
        });
        Log.d(TAG, "onAttachedToWindow mListD5:" + touchRectList.size());

    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        for (int i = 0; i < touchRectList.size(); i++) {
            D5 d5 = touchRectList.get(i).getTouchView();
            Log.w(TAG, "onWindowVisibilityChanged W:" + d5.getMeasuredWidth() + " H:" + d5.getMeasuredHeight());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        for (int i = 0; i < touchRectList.size(); i++) {
            D5 d5 = touchRectList.get(i).getTouchView();
            Log.i(TAG, "onWindowFocusChanged W:" + d5.getMeasuredWidth() + " H:" + d5.getMeasuredHeight());
        }
    }

    Canvas drawCanvas;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawCanvas = canvas;
        Log.d(TAG, "onDraw mListD5:" + touchRectList.size());
        for (int i = 0; i < touchRectList.size(); i++) {
            canvas.save();
            TouchViewRect touchViewRect = touchRectList.get(i);
            canvas.translate(touchViewRect.getStartX(), touchViewRect.getStartY());
            touchViewRect.getTouchView().draw(canvas);
            canvas.restore();
        }
    }
}
