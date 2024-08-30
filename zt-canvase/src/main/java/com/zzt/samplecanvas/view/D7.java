package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2021/7/16
 */
public class D7 extends View {
    public D7(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPath();
    }

    public D7(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    private Paint paint;
    private Paint paintPoint;
    private int cornerRadius;

    private void initPath() {


        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(10);
        cornerRadius = 20; // 圆角半径，可自定义

        paintPoint = new Paint();
        paintPoint.setColor(Color.BLUE);
        paintPoint.setStyle(Paint.Style.FILL);
        paintPoint.setAntiAlias(true);
        paintPoint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        Path path = new Path();
//        path.moveTo(width, 0);
//        path.lineTo((width - width / 4), height / 2);
        RectF rectf = new RectF(width / 4, height / 2, (width - width / 4), (height / 2 + height / 4));
        path.arcTo(rectf, 0, 180);

//        path.lineTo(width / 4, height / 2);
//        path.lineTo(0, 0);

//        path.addArc(rectf, 60, 60);

//        path.moveTo(0, height);
//        path.lineTo(width / 2, cornerRadius);
//        // 添加圆角
//        path.addArc(width / 2 - cornerRadius, 0, width / 2 + cornerRadius, cornerRadius * 2, -180, 90);
//        path.lineTo(width, height);
//        // 添加圆角
//        path.addArc(width - cornerRadius, height - cornerRadius * 2, width, height, -90, 90);
//        path.close();

        canvas.drawPath(path, paint);
    }
}