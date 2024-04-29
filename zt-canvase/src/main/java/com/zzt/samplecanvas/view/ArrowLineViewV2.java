package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: zeting
 * @date: 2023/5/24
 * 画出带有箭头直线 三角箭头
 */
public class ArrowLineViewV2 extends View {
    private Paint paint;
    private Paint pointPaint;
    private Point startPoint;
    private Point endPoint;

    public ArrowLineViewV2(Context context) {
        super(context);
        init();
    }

    public ArrowLineViewV2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);


        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeWidth(8);

        setPoints(new Point(400, 700), new Point(600, 300));
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

        /**
         * Math.PI 表示一个圆的周长与直径的比例，约为 3.141592653589793，其实就是我们所说的圆周率 π
         *
         * Math.sin(x) //x 的正玄值。返回值在 -1.0 到 1.0 之间；
         *
         * Math.cos(x) //x 的余弦值。返回的是 -1.0 到 1.0 之间的数；
         * 角度和弧度转换公式：
         *
         * 　　弧度 = 角度*PI/180
         * 　　角度 = 弧度*180/PI
         *
         * 还有一个计算弧度常用的：（一样的）
         *
         * 　　弧度的计算公式为： 2*PI/360*角度；
         */


        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        startPoint = new Point(100, 100);
        endPoint = new Point(600, 300);

        int arrowSize = 100;
        Point[] arrowOtherPoint = getArrowOtherPoint(startPoint, endPoint, arrowSize);

        // Draw the line
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(path, paint);
        // Draw the arrow
        Path arrowPath = new Path();
        arrowPath.moveTo(endPoint.x, endPoint.y);
        arrowPath.lineTo(arrowOtherPoint[0].x, arrowOtherPoint[0].y);
        arrowPath.lineTo(arrowOtherPoint[1].x, arrowOtherPoint[1].y);
        arrowPath.close();
        canvas.drawPath(arrowPath, paint);


        startPoint = new Point(100, 400);
//        endPoint = new Point(600, 400);
        endPoint = new Point(600, 600);
//        endPoint = new Point(100, 800);

        Point[] arrowOtherPoint2 = getArrowOtherPoint2(startPoint, endPoint, arrowSize);
        // Draw the line
        Path path2 = new Path();
        path2.moveTo(startPoint.x, startPoint.y);
        path2.lineTo(endPoint.x, endPoint.y);
        canvas.drawPath(path2, paint);
        // Draw the arrow
        Path arrowPath2 = new Path();
        arrowPath2.moveTo(arrowOtherPoint2[2].x, arrowOtherPoint2[2].y);
        arrowPath2.lineTo(arrowOtherPoint2[0].x, arrowOtherPoint2[0].y);
        arrowPath2.lineTo(arrowOtherPoint2[1].x, arrowOtherPoint2[1].y);
        arrowPath2.close();
        canvas.drawPath(arrowPath2, paint);

        pointPaint.setColor(Color.RED);
        canvas.drawCircle(arrowOtherPoint2[2].x, arrowOtherPoint2[2].y, 10, pointPaint);

        pointPaint.setColor(Color.GREEN);
        canvas.drawCircle(endPoint.x, endPoint.y, 10, pointPaint);


    }


    public Point[] getArrowOtherPoint2(Point startPoint, Point endPoint, float arrowSize) {
        // Calculate the angle of the line
        double angle = Math.atan2(endPoint.y - startPoint.y, endPoint.x - startPoint.x);
        // Calculate arrow coordinates
        int arrowAngle = 30;
        // 第三个点
        double subLength = arrowSize * Math.cos(Math.toRadians(arrowAngle));
        int arrowX3 = (int) (endPoint.x - subLength * Math.cos(angle));
        int arrowY3 = (int) (endPoint.y - subLength * Math.sin(angle));
        Point calcPoint = new Point(arrowX3, arrowY3);

        int arrowX1 = (int) (calcPoint.x + arrowSize * Math.cos(angle - Math.toRadians(arrowAngle)));
        int arrowY1 = (int) (calcPoint.y + arrowSize * Math.sin(angle - Math.toRadians(arrowAngle)));
        int arrowX2 = (int) (calcPoint.x + arrowSize * Math.cos(angle + Math.toRadians(arrowAngle)));
        int arrowY2 = (int) (calcPoint.y + arrowSize * Math.sin(angle + Math.toRadians(arrowAngle)));

        return new Point[]{new Point(arrowX1, arrowY1), new Point(arrowX2, arrowY2), calcPoint};
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


    public void ssss() {

        // 将经纬度转换为笛卡尔坐标系下的值
        double lat1 = 40.7128; // 点1的纬度
        double lon1 = -74.0060; // 点1的经度
        double lat2 = 34.0522; // 点2的纬度
        double lon2 = -118.2437; // 点2的经度

        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);

        // 计算夹角
        float bearing = location1.bearingTo(location2);

        // 根据夹角和半径计算线段上的点
        double radius = 1000; // 半径（单位：米）
        double distance = radius / 111000f; // 距离（单位：经纬度）

        double lat3 = lat1 + distance * Math.cos(Math.toRadians(bearing));
        double lon3 = lon1 + distance * Math.sin(Math.toRadians(bearing));

// 输出结果
        System.out.println("线段上的点的纬度：" + lat3);
        System.out.println("线段上的点的经度：" + lon3);
    }

}