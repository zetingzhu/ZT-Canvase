package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2023/5/31
 */
public class DDD1 extends View {
    public DDD1(Context context) {
        super(context);
    }

    public DDD1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DDD1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int canvasWidth = 200;
        int canvasHeight = 200;

        // 创建画笔
        Paint paint = new Paint();
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);

        // 创建图形的路径
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 100);
        path.lineTo(100, 150);
        path.lineTo(200, 100);
        path.lineTo(200, 0);
        path.lineTo(100, 0);
        path.close();


        // 绘制不规则图形
//        canvas.drawPath(path, paint);
        // 保存画布状态
        canvas.save();

        // 创建裁剪路径
//        Path clipPath = new Path();
//        clipPath.addRect(22, 11, canvasWidth, canvasHeight, Path.Direction.CW);
//        clipPath.op(path, Path.Op.DIFFERENCE);
//
//        canvas.clipRect(45, 22, canvasWidth, canvasHeight - 45);

        // 裁剪画布
//        canvas.clipPath(clipPath);

        // 绘制不规则图形
        canvas.drawPath(path, paint);

        // 恢复画布状态
        canvas.restore();
    }
}
