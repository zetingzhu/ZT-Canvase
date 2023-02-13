package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
public class D2 extends View {
    public D2(Context context) {
        super(context);
    }

    public D2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public D2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        int l = 10;
        int t = 10;
        int r = 410;
        int b = 410;
        int space = 30;
        Rect squareRect = new Rect(l, t, r, b);
        int squareCount = (r - l) / space;
        float px = l + (r - l) / 2;
        float py = t + (b - t) / 2;
        for (int i = 0; i < squareCount; i++) {
            // 保存画布
            canvas.save();
            float fraction = (float) i / squareCount;
            // 将画布以正方形中心进行缩放
            canvas.scale(fraction, fraction, px, py);
            canvas.drawRect(squareRect, paint);
            // 画布回滚
            canvas.restore();
        }
    }
}
