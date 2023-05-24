package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: zeting
 * @date: 2023/5/24
 * 画出带有箭头直线 三角箭头
 */
public class ArrowLineView extends View {
    private Paint paint;
    private Point startPoint;
    private Point endPoint;

    public ArrowLineView(Context context) {
        super(context);
        init();
    }

    public ArrowLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        setPoints(new Point(100, 100), new Point(300, 300));
    }

    public void setPoints(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        invalidate(); // Trigger redraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (startPoint == null || endPoint == null)
            return;

        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.lineTo(endPoint.x, endPoint.y);

        // Calculate the angle of the line
        double angle = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);

        // Calculate arrow coordinates
        int arrowSize = 60;
        int arrowAngle = 30;
        int arrowX1 = (int) (endPoint.x - arrowSize * Math.cos(angle - Math.toRadians(arrowAngle)));
        int arrowY1 = (int) (endPoint.y - arrowSize * Math.sin(angle - Math.toRadians(arrowAngle)));
        int arrowX2 = (int) (endPoint.x - arrowSize * Math.cos(angle + Math.toRadians(arrowAngle)));
        int arrowY2 = (int) (endPoint.y - arrowSize * Math.sin(angle + Math.toRadians(arrowAngle)));

        // Draw the line
        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        // Draw the arrow
        Path arrowPath = new Path();
        arrowPath.moveTo(endPoint.x, endPoint.y);
        arrowPath.lineTo(arrowX1, arrowY1);
        arrowPath.lineTo(arrowX2, arrowY2);
        arrowPath.close();
        canvas.drawPath(arrowPath, paint);
    }


    public Point[] getArrowOtherPoint(Point startPoint, Point endPoint, int arrowSize) {
        // Calculate the angle of the line
        double angle = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);

        // Calculate arrow coordinates
        int arrowAngle = 30;
        int arrowX1 = (int) (endPoint.x - arrowSize * Math.cos(angle - Math.toRadians(arrowAngle)));
        int arrowY1 = (int) (endPoint.y - arrowSize * Math.sin(angle - Math.toRadians(arrowAngle)));
        int arrowX2 = (int) (endPoint.x - arrowSize * Math.cos(angle + Math.toRadians(arrowAngle)));
        int arrowY2 = (int) (endPoint.y - arrowSize * Math.sin(angle + Math.toRadians(arrowAngle)));

        return new Point[]{new Point(arrowX1, arrowY1), new Point(arrowX2, arrowY2)};
    }

}