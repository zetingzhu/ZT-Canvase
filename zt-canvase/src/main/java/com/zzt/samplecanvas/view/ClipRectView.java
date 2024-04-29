package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2023/6/12
 */
public class ClipRectView extends View {
    private Paint mPaint;

    public ClipRectView(Context context) {
        super(context);
        initView(context);
    }

    public ClipRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ClipRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
    }

    /**
     * Op.DIFFERENCE，实际上就是求得的A和B的差集范围，即A－B，只有在此范围内的绘制内容才会被显示；
     * Op.REVERSE_DIFFERENCE，实际上就是求得的B和A的差集范围，即B－A，只有在此范围内的绘制内容才会被显示；；
     * Op.INTERSECT，即A和B的交集范围，只有在此范围内的绘制内容才会被显示；
     * Op.REPLACE，不论A和B的集合状况，B的范围将全部进行显示，如果和A有交集，则将覆盖A的交集范围；
     * Op.UNION，即A和B的并集范围，即两者所包括的范围的绘制内容都会被显示；
     * Op.XOR，A和B的补集范围，此例中即A除去B以外的范围，只有在此范围内的绘制内容才会被显示
     *
     //        canvas.clipRect(new Rect(100, 100, 400, 200));
     //        canvas.clipRect(new Rect(180, 180, 350, 190), Region.Op.DIFFERENCE);
     //        canvas.clipRect(new Rect(100, 100, 400, 200));
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.DIFFERENCE);
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.REVERSE_DIFFERENCE);
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.INTERSECT);
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.REPLACE);
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.UNION);
     //        canvas.clipRect(new Rect(100, 100, 500, 200), Region.Op.XOR);

     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.clipRect(new Rect(50, 100, 600, 500));

        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, 200, 200, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(450, 400, 400, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(300, 0, 700, 400, mPaint);

//        // 裁剪形状 A
//        canvas.clipRect(new RectF(0, 0, 300, 300));
//        // 画布裁剪一个圆形
//        Path mPath = new Path();
//        mPath.addCircle(280, 190, 150, Path.Direction.CCW);
//        // 裁剪形状 B
////        canvas.clipPath(mPath, Region.Op.DIFFERENCE);
////        canvas.clipPath(mPath, Region.Op.REVERSE_DIFFERENCE);
////        canvas.clipPath(mPath, Region.Op.INTERSECT);
////        canvas.clipPath(mPath, Region.Op.REPLACE);
////        canvas.clipPath(mPath, Region.Op.UNION);
//        canvas.clipPath(mPath, Region.Op.XOR);
//
//        mPaint.setColor(Color.parseColor("#FF4081"));
//        canvas.drawRect(new RectF(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE), mPaint);
        canvas.restore();
    }
}
