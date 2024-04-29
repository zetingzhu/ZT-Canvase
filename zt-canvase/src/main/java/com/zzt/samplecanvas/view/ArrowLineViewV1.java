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
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zeting
 * @date: 2023/5/24
 * 画出带有箭头直线 三角箭头
 */
public class ArrowLineViewV1 extends View {
    private static final String TAG = ArrowLineViewV1.class.getSimpleName();
    private Paint paint;
    private Point startPoint;
    private Point endPoint;

    public ArrowLineViewV1(Context context) {
        super(context);
        init();
    }

    public ArrowLineViewV1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        setPoints(new Point(100, 100), new Point(300, 300));

        sssss();

        bbbbbb();
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


    public void bbbbbb() {
        // 已知两点和矩形边框
        int x1 = 10;
        int y1 = 10;
        int x2 = 290;
        int y2 = 180;

        int left = 0;
        int top = 0;
        int right = 300;
        int bottom = 200;

        // 计算两点之间的直线方程
        float k = (float) (y2 - y1) / (x2 - x1);
        float b = y1 - k * x1;

        // 计算两点延长线与矩形上下左右四条边的交点
        float[] points = new float[8];
        points[0] = (top - b) / k;  // 上边交点的 x 坐标
        points[1] = top;            // 上边交点的 y 坐标
        points[2] = (bottom - b) / k;  // 下边交点的 x 坐标
        points[3] = bottom;         // 下边交点的 y 坐标
        points[4] = left;           // 左边交点的 x 坐标
        points[5] = k * left + b;   // 左边交点的 y 坐标
        points[6] = right;          // 右边交点的 x 坐标
        points[7] = k * right + b;  // 右边交点的 y 坐标

        // 判断交点是否在矩形内部
        List<Float> intersectPoints = new ArrayList<>();
        for (int i = 0; i < points.length; i += 2) {
            float x = points[i];
            float y = points[i + 1];
            if (x >= left && x <= right && y >= top && y <= bottom) {
                intersectPoints.add(x);
                intersectPoints.add(y);
            }
        }

        // 选择距离最近的交点
        float minDistance = Float.MAX_VALUE;
        float intersectX = 0;
        float intersectY = 0;
        for (int i = 0; i < intersectPoints.size(); i += 2) {
            float x = intersectPoints.get(i);
            float y = intersectPoints.get(i + 1);
            float distance = (x - x1) * (x - x1) + (y - y1) * (y - y1);
            if (distance < minDistance) {
                minDistance = distance;
                intersectX = x;
                intersectY = y;
            }
        }

        // 输出结果
        Log.d(TAG, "相交点坐标：(" + intersectX + ", " + intersectY + ")");
    }

    public void sssss() {
        // 定义两个点的坐标
        float x1 = 100f;
        float y1 = 10f;


        float x2 = 500f;
        float y2 = 110f;

// 定义矩形的边界坐标
        float left = 0f;
        float top = 0f;
        float right = 600f;
        float bottom = 300f;

// 创建RectF对象
        RectF rect = new RectF(left, top, right, bottom);

// 获取交点坐标
        PointF[] calcPoints = findIntersectionPoints(x1, y1, x2, y2, rect);


        for (int i = 0; i < calcPoints.length; i++) {
            PointF calcPoint = calcPoints[i];
            if (calcPoint !=null){

            }
        }


        Log.d("sss", "s");
    }

    public static PointF[] findIntersectionPoints(float x1, float y1, float x2, float y2, RectF rect) {
        PointF[] intersectionPoints = new PointF[4];

        // 计算直线的斜率
        float m = (y2 - y1) / (x2 - x1);

        // 计算直线与上边界的交点坐标
        float x = x1 + (rect.top - y1) / m;
        if (rect.left <= x && x <= rect.right) {
            intersectionPoints[0] = new PointF(x, rect.top);
        }

        // 计算直线与下边界的交点坐标
        x = x1 + (rect.bottom - y1) / m;
        if (rect.left <= x && x <= rect.right) {
            intersectionPoints[1] = new PointF(x, rect.bottom);
        }

        // 计算直线与左边界的交点坐标
        float y = y1 + m * (rect.left - x1);
        if (rect.top <= y && y <= rect.bottom) {
            intersectionPoints[2] = new PointF(rect.left, y);
        }

        // 计算直线与右边界的交点坐标
        y = y1 + m * (rect.right - x1);
        if (rect.top <= y && y <= rect.bottom) {
            intersectionPoints[3] = new PointF(rect.right, y);
        }

        return intersectionPoints;
    }

//    public void main(String[] args) {
//        // 两个点的坐标
//        double x1 = 2.0;
//        double y1 = 3.0;
//        double x2 = 7.0;
//        double y2 = 9.0;
//
//        // 矩形的边界坐标
//        double xLeft = 1.0;
//        double yTop = 5.0;
//        double xRight = 9.0;
//        double yBottom = 2.0;
//
//        // 创建直线对象
//        Line2D line = new Line2D.Double(x1, y1, x2, y2);
//
//        // 创建矩形对象
//        Rectangle2D rect = new Rectangle2D.Double(xLeft, yTop, xRight - xLeft, yBottom - yTop);
//
//        // 判断直线与矩形是否相交
//        if (line.intersects(rect)) {
//            // 获取直线与矩形的交点坐标
//            Point2D[] intersectionPoints = getIntersectionPoints(line, rect);
//            for (Point2D point : intersectionPoints) {
//                System.out.println("Intersection point: (" + point.getX() + ", " + point.getY() + ")");
//            }
//        } else {
//            System.out.println("The line does not intersect the rectangle.");
//        }
//    }
//
//    public static Point2D[] getIntersectionPoints(Line2D line, Rectangle2D rect) {
//        Point2D[] intersectionPoints = new Point2D[2];
//
//        intersectionPoints[0] = line.intersectsLine(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMinY()) ?
//                line.intersect(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMinY()) : null;
//        intersectionPoints[1] = line.intersectsLine(rect.getMinX(), rect.getMaxY(), rect.getMaxX(), rect.getMaxY()) ?
//                line.intersect(rect.getMinX(), rect.getMaxY(), rect.getMaxX(), rect.getMaxY()) : null;
//
//        return intersectionPoints;
//    }


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