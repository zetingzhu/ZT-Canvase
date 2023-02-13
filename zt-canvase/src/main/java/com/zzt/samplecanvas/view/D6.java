package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2021/7/16
 */
public class D6 extends View {
    public D6(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPath();
    }

    public D6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    private Path path;
    private Paint paint;

    private void initPath() {
        path = new Path();
        path.lineTo(100, 0);
        path.moveTo(0, 0);
        path.lineTo(0, 100);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    //不建议在onDraw 实例对象，因为onDraw会频繁调用，所以才单独抽取出方法initPath
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        for (int i = 0; i < 5; i++) {
//            //打开 canvas.save(); 和 //canvas.restore(); 可以看到不同效果
////            canvas.save();
//            canvas.translate(60, 60);
//            canvas.drawPath(path, paint);
//            //restore要配合save使用，save入栈，restore出栈，如果没有save，直接restore会报错
////            canvas.restore();
//        }

        int beginSave=canvas.save();
        canvas.translate(100,0);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.translate(50,50);
            canvas.drawPath(path,paint);
        }
        canvas.restoreToCount(beginSave);
        canvas.drawPath(path,paint);

    }
}