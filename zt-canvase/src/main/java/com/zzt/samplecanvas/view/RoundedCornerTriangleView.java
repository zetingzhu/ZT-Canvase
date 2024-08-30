package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2024/8/30
 */


public class RoundedCornerTriangleView extends View {

    private Paint paint;
    private Paint paint2;
    private Paint paintP1;
    private Paint paintP2;
    private Paint paintP3;
    private Path path;
    private Path path2;
    private float cornerRadius;

    public RoundedCornerTriangleView(Context context) {
        super(context);
        init();
    }

    public RoundedCornerTriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedCornerTriangleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW); // 设置线条颜色
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE); // 设置填充模式

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.DKGRAY); // 设置线条颜色
        paint2.setStrokeWidth(20);
        paint2.setStyle(Paint.Style.STROKE); // 设置填充模式

        paintP1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintP1.setColor(Color.RED); // 设置线条颜色
        paintP1.setStrokeWidth(80);
        paintP1.setStyle(Paint.Style.FILL); // 设置填充模式

        paintP2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintP2.setColor(Color.BLUE); // 设置线条颜色
        paintP2.setStrokeWidth(80);
        paintP2.setStyle(Paint.Style.FILL); // 设置填充模式

        paintP3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintP3.setColor(Color.GREEN); // 设置线条颜色
        paintP3.setStrokeWidth(80);
        paintP3.setStyle(Paint.Style.FILL); // 设置填充模式

        cornerRadius = 10f; // 圆角半径，根据需要调整
        path = new Path();
        path2 = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();  // 三角形宽度
        int height = getHeight(); // 三角形高度


        // 绘制三角形路径
//        path.moveTo(0f, height);       // 左下角
//        path.lineTo(width / 2, 0f);   // 顶部
//        path.lineTo(width, height);    // 右下角
//        path.close();           // 闭合路径


        // 画圆弧方法不行
//        int halfWidth = width / 2;
//        int top = 0;
//        RectF arcRect = new RectF(0, height - halfWidth, width, height);
//        path.arcTo(arcRect, 0, 359);
//        canvas.drawPoint(0, height - halfWidth, paintP1);
//        canvas.drawPoint(width, height, paintP2);


        // 画贝塞尔曲线
//        path2.moveTo(0f, height);       // 左下角
//        path2.quadTo(width / 2, 0, width, height);
//        path2.close();           // 闭合路径
//        // 绘制路径
//        canvas.drawPath(path, paint);
//        canvas.drawPath(path2, paint2);
//        canvas.drawPoint(0, height, paintP1);
//        canvas.drawPoint(width / 2, 0, paintP2);
//        canvas.drawPoint(width, height, paintP3);
//        PointF scalePoint = getScalePoint(new PointF(0, height), new PointF(width / 2, 0), 0.5f);
//        canvas.drawPoint(scalePoint.x, scalePoint.y, paintP1);


        PointF start1 = new PointF(0f, height);
        PointF start2 = new PointF(width, height);
        PointF top = new PointF(width / 2, 0f);
        // 比例
        float scale = 0.8f;


        PointF q1 = getScalePoint(start1, top, scale);
        PointF q2 = getScalePoint(start2, top, scale);
        canvas.drawPoint(q1.x, q1.y, paintP1);
        canvas.drawPoint(q2.x, q2.y, paintP2);

        path.moveTo(start1.x, start1.y);
        path.lineTo(q1.x, q1.y);
        path.quadTo(top.x, top.y, q2.x, q2.y);
        path.lineTo(q2.x, q2.y);
        path.lineTo(start2.x, start2.y);
        path.close();
        canvas.drawPath(path, paint);


        canvas.drawPoint(start1.x, start1.y, paintP1);
        canvas.drawPoint(start2.x, start2.y, paintP2);
        canvas.drawPoint(top.x, top.y, paintP3);

    }


    /**
     * 获取圆角三角形路径
     *
     * @param start1 三角形第一个底点，
     * @param start2 三角形第二个底点，
     * @param top    三角形高点
     * @param scale  圆角开始占边长比例
     * @return
     */
    public Path getRoundTrianglePath(PointF start1, PointF start2, PointF top, float scale) {
        PointF q1 = getScalePoint(start1, top, scale);
        PointF q2 = getScalePoint(start2, top, scale);

        path.moveTo(start1.x, start1.y);
        path.lineTo(q1.x, q1.y);
        path.quadTo(top.x, top.y, q2.x, q2.y);
        path.lineTo(q2.x, q2.y);
        path.lineTo(start2.x, start2.y);
        return path;
    }


    public PointF getScalePoint(PointF point1, PointF point2, float scale) {
        float x = point1.x + (point2.x - point1.x) * scale;
        float y = point1.y + (point2.y - point1.y) * scale;
        return new PointF(x, y);
    }

}
