package com.zzt.samplecanvas.sample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.zzt.samplecanvas.R;

/**
 * @author: zeting
 * @date: 2021/8/2
 */
public class ViewV1 extends View {
    private static final String TAG = ViewV1.class.getSimpleName();

    private int viewValue = 0;

    public ViewV1(Context context) {
        this(context, null, 0);
    }

    public ViewV1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewV1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CanvasView, defStyleAttr, 0);
        viewValue = (int) attributes.getInt(R.styleable.CanvasView_viewValue, 0);
        attributes.recycle();

        initView(context);
    }

    Paint bgPaint;
    Paint paint;
    Paint textPaint;
    Bitmap bitmap;
    private static int W = 250;
    private static int H = 250;
    private static final int ROW_MAX = 4;   // number of samples per row
    private Bitmap mSrcB;// source源像素
    private Bitmap mDstB;// destination 目标像素
    // background checker-board pattern
    private static final Xfermode[] sModes = {
            new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
            new PorterDuffXfermode(PorterDuff.Mode.SRC),
            new PorterDuffXfermode(PorterDuff.Mode.DST),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
            new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
            new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
            new PorterDuffXfermode(PorterDuff.Mode.XOR),
            new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
            new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
            new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
            new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    };
    private static final String[] sLabels = {
            "Clear", "Src", "Dst", "SrcOver",
            "DstOver", "SrcIn", "DstIn", "SrcOut",
            "DstOut", "SrcATop", "DstATop", "Xor",
            "Darken", "Lighten", "Multiply", "Screen"
    };

    private void initView(Context context) {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setTextSize(5);
        bgPaint.setColor(Color.parseColor("#C2000000"));

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(50);//设置画笔宽度 ，单位px
        paint.setColor(Color.BLUE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.RED);


        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cursor);
        mSrcB = makeSrc(W, H);
        mDstB = makeDst(W, H);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPg(canvas);

        switch (viewValue) {
            case 0:
                /**
                 * 设置路据填充模式
                 */
                drawCircle(canvas);
                break;
            case 1:
                /**
                 * 设置文字绘制对齐模式
                 */
                drawText(canvas);
                break;
            case 2:
                /**
                 * 设置线段连接模式
                 */
                drawJoin(canvas);
                break;
            case 3:
                /**
                 * 设置线头
                 */
                drawCap(canvas);
                break;
            case 4:
                /**
                 * 设置拐角路据
                 */
                drawPathEffect(canvas);
                break;
            case 5:
                /**
                 * 绘制虚线效果
                 */
                drawDashPathEffect(canvas);
                break;
            case 6:
                /**
                 *  使用path 绘制虚线
                 */
                drawPathDashPathEffect(canvas);
                break;
            case 7:
                /**
                 * 线性渐变
                 */
                drawLinearGradient(canvas);
                break;
            case 8:
                /**
                 * 径向渐变
                 */
                drawRadialGradient(canvas);
                break;
            case 9:
                /**
                 * 扫描渐变
                 */
                drawSweepGradient(canvas);
                break;
            case 10:
                /**
                 * 混合渐变
                 */
                drawComposeShader(canvas);
                break;
            case 11:
                /**
                 * 阴影绘制
                 */
                drawShadowLayer(canvas);
                break;
            case 12:
                /**
                 *  PorterDuff.Mode 混合模式
                 */
                drawPorterDuff(canvas);
                break;
            case 13:
                /**
                 * 画线段
                 */
                drawCanvasLine(canvas);
                break;
            case 14:
                /**
                 * 画点
                 */
                drawCanvasPoint(canvas);
                break;
            case 15:
                /**
                 * 画矩形框和背景图
                 */
                canvasDrawRect(canvas);
                break;
            case 16:
                /**
                 * 圆
                 */
                canvasDrawCircle(canvas);
                break;
            case 17:
                /**
                 * 椭圆
                 */
                canvasDrawOval(canvas);
                break;
            case 18:
                /**
                 * 圆弧
                 */
                canvasDrawArc(canvas);
                break;
            case 19:
                /**
                 * 折线，曲线，圆弧路径
                 */
                canvasPath(canvas);
                break;
            case 20:
                /**
                 * 路径文案
                 */
                canvasDrawText(canvas);
                break;
        }

//        drawCircle(canvas);
//        drawText(canvas);
//        drawJoin(canvas);
//        drawCap(canvas);
//        drawPathEffect(canvas);
//        drawDashPathEffect(canvas);
//        drawPathDashPathEffect(canvas);
//        drawLinearGradient(canvas);
//        drawRadialGradient(canvas);
//        drawSweepGradient(canvas);
//        drawComposeShader(canvas);
//        drawShadowLayer(canvas);
//        drawPorterDuff(canvas);
//        drawCanvasLine(canvas);
//        drawCanvasPoint(canvas);
//        canvasDrawRect(canvas);
//        canvasDrawCircle(canvas);
//        canvasDrawOval(canvas);
//        canvasDrawArc(canvas);
//        canvasPath(canvas);
//        canvasDrawText(canvas);
    }

    private void canvasDrawText(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawCircle(500, 300, 20, paint);

        textPaint.setTextAlign(Paint.Align.CENTER);
        String text = "这个是测试文案";
        canvas.drawText(text, 500, 300, textPaint);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        Log.d(TAG, "Paint.FontMetrics top:" + fontMetrics.top);
        Log.d(TAG, "Paint.FontMetrics ascent:" + fontMetrics.ascent);
        Log.d(TAG, "Paint.FontMetrics descent:" + fontMetrics.descent);
        Log.d(TAG, "Paint.FontMetrics bottom:" + fontMetrics.bottom);
        Log.d(TAG, "Paint.FontMetrics leading:" + fontMetrics.leading);

        Log.e(TAG, "W:" + textPaint.measureText(text) + " H:" + (fontMetrics.bottom - fontMetrics.top));

        Rect rect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), rect);
        Log.w(TAG, "text Rect:" + rect.toString());
        Log.e(TAG, "W:" + (rect.right - rect.left) + " H:" + (rect.bottom - rect.top));

        RectF rectF = new RectF(200, 500, 700, 700);
        canvas.drawRect(rectF, paint);

        //计算baseline
//        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
//        float baseline = rectF.centerY() + distance;
        float baseline = rectF.centerY() - fontMetrics.top / 2 - fontMetrics.bottom / 2;
        canvas.drawText(text, rectF.centerX(), baseline, textPaint);

        canvas.drawCircle(500, 900, 20, paint);
        String showText = "折戟沉沙铁未销，自将磨洗认前朝。东风不与周郎便，铜雀春深锁二乔。";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            canvas.drawTextRun(showText, 0, showText.length(), 0, showText.length(), 500, 900, true, textPaint);
        }


        Path path = new Path();
        rectF = new RectF(200, 1000, 900, 1200);
        path.addRoundRect(rectF, 10, 10, Path.Direction.CCW);
        canvas.drawTextOnPath(showText, path, 0, 0, textPaint);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rectF, paint);
    }

    private void canvasPath(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(300, 200);
        path.lineTo(500, 200);
        path.setLastPoint(700, 800);
        path.close();
        canvas.drawPath(path, paint);

        /**
         * Direction的意思是方向，指导，趋势。点进去跟一下你会发现Direction是一个枚举类型（Enum）
         * 分别有CW（顺时针），CCW（逆时针）两个常量
         */
        path.addRect(100, 600, 400, 800, Path.Direction.CCW);
        path.setLastPoint(200, 400);
        canvas.drawPath(path, paint);


        path.addArc(500, 600, 900, 800, 30, 100);
        canvas.drawPath(path, paint);

        path.arcTo(500, 600, 900, 800, 190, 100, false);
        canvas.drawPath(path, paint);

        path.moveTo(600, 400);
        path.quadTo(800, 200, 1000, 300);

        canvas.drawPath(path, paint);

    }

    /**
     * drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint)
     * drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint)
     * <p>
     * oval ： 生成椭圆的矩形
     * startAngle ： 弧开始的角度 （X轴正方向为0度，顺时针弧度增大）
     * sweepAngle ： 绘制多少弧度 （注意不是结束弧度）
     * useCenter ： 是否有弧的两边 true有两边 false无两边
     */
    private void canvasDrawArc(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        RectF rectF = new RectF(100, 200, 600, 700);
        canvas.drawArc(rectF, 0, 150, false, paint);
    }


    private void canvasDrawOval(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(100);
        canvas.drawOval(200, 200, 800, 400, paint);

        RectF rectF = new RectF(100, 600, 500, 700);
        canvas.drawOval(rectF, paint);
    }

    private void canvasDrawCircle(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(100);
        canvas.drawCircle(300, 300, 100, paint);
    }

    private void canvasDrawRect(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawRect(100, 100, 1000, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100, 300, 1000, 500, paint);


        RectF rectF = new RectF(100, 600, 500, 700);
        canvas.drawRect(rectF, paint);

        Rect rect = new Rect(600, 600, 1000, 700);
        canvas.drawRect(rect, paint);

        // RectF ： 绘制的矩形
        //rx ： 生成圆角的椭圆X轴半径
        //ry ： 生成圆角的椭圆Y轴的半径
        RectF rectF1 = new RectF(100, 800, 500, 900);
        canvas.drawRoundRect(rectF1, 30, 30, paint);

        canvas.drawRoundRect(600, 800, 1000, 900, 10, 10, paint);
    }

    private void drawCanvasPoint(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawPoint(100, 100, paint);
        canvas.drawPoints(new float[]{100, 400, 500, 400, 400, 500, 900, 500, 50, 700, 750, 800}, paint);
    }

    private void drawCanvasLine(Canvas canvas) {
        paint.setStrokeWidth(30);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawLine(100, 100, 1000, 100, paint);

        canvas.drawLines(new float[]{100, 200, 500, 200, 400, 300, 900, 300}, paint);
        //new float[]{  500, 400, 400, 500, 900, 500, 50, 700 }
        canvas.drawLines(new float[]{100, 400, 500, 400, 400, 500, 900, 500, 50, 700, 750, 800}, 2, 8, paint);
    }


    /**
     * PorterDuff.Mode 混合模式
     */
    private void drawPorterDuff(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelP.setTextAlign(Paint.Align.CENTER);
        labelP.setTextSize(30);
        Paint paint = new Paint();
        paint.setFilterBitmap(false);
        canvas.translate(30, 70);
        int x = 0;
        int y = 0;
        for (int i = 0; i < sModes.length; i++) {
            //画背景
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.parseColor("#BBB4ED9F"));
            canvas.drawRect(x, y, x + W, y + H, paint);

            int sc = canvas.saveLayer(x, y, x + W, y + H, null);

            canvas.translate(x, y);
            canvas.drawBitmap(mDstB, 0, 0, paint);

            paint.setXfermode(sModes[i]);
            canvas.drawBitmap(mSrcB, 0, 0, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(sc);

            canvas.drawText(sLabels[i], x + W / 2, y - labelP.getTextSize() / 2, labelP);
            x += W + 10;
            if ((i % ROW_MAX) == ROW_MAX - 1) {
                x = 0;
                y += H + 70;
            }
        }
    }


    /**
     * 绘制目标图像圆
     */
    Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
        return bm;
    }

    /**
     * 绘制源图像方块
     */
    Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);
        c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
        return bm;
    }


    private void drawShadowLayer(Canvas canvas) {
        /**
         * float radius 为模糊半径，入宫为0移除阴影
         */
        textPaint.setShadowLayer(2, 20, 30, Color.GRAY);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("android 阴影绘制", getWidth() / 2, 450, textPaint);
    }

    /**
     * 混合渐变
     *
     * @param canvas
     */
    private void drawComposeShader(Canvas canvas) {
        paint.setStrokeWidth(50);//设置画笔宽度 ，单位px
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        Shader shader1 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        LinearGradient shader2 = new LinearGradient(0, 0, 500, 0, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        // ComposeShader：结合两个 Shader
        Shader shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.SCREEN);
        paint.setShader(shader);

        canvas.drawRect(0, 0, getRight(), getBottom(), paint);
    }


    /**
     * 扫描渐变
     *
     * @param canvas
     */
    private void drawSweepGradient(Canvas canvas) {
        paint.setStrokeWidth(50);//设置画笔宽度 ，单位px
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //两个颜色
        SweepGradient sweepGradient = new SweepGradient(400, 400, Color.RED, Color.BLUE);
        paint.setShader(sweepGradient);
        canvas.drawCircle(400, 400, 300, paint);

        //多种颜色
        sweepGradient = new SweepGradient(400, 1200,
                new int[]{Color.RED, Color.YELLOW, Color.GRAY, Color.BLUE}, null);
        paint.setShader(sweepGradient);
        canvas.drawCircle(400, 1200, 300, paint);
    }

    /**
     * 径向渐变
     *
     * @param canvas
     */
    private void drawRadialGradient(Canvas canvas) {
        paint.setStrokeWidth(50);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(200);

        //两个颜色
        RadialGradient radialGradient = new RadialGradient(400, 400, 300, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(400, 400, 300, paint);

        //多种颜色
        radialGradient = new RadialGradient(400, 1200, 300,
                new int[]{Color.RED, Color.YELLOW, Color.GRAY, Color.BLUE}, new float[]{0, 0.4f, 0.8f, 1}, Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawCircle(400, 1200, 300, paint);
    }

    /**
     * 线性渐变
     *
     * @param canvas
     */
    private void drawLinearGradient(Canvas canvas) {
        paint.setStrokeWidth(50);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        LinearGradient linearGradient = new LinearGradient(100, 100, 300, 100, Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
        canvas.drawLine(100, 100, 1000, 100, paint);

        linearGradient = new LinearGradient(100, 200, 800, 800, Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawRect(100, 200, 800, 800, paint);


        linearGradient = new LinearGradient(100, 100, 800, 100,
                new int[]{Color.RED, Color.YELLOW, Color.BLUE}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);  //设置渐变区域 属性
        paint.setShader(linearGradient);
        canvas.drawLine(100, 900, 800, 900, paint);
    }

    /**
     * 使用path 绘制虚线
     *
     * @param canvas
     */
    private void drawPathDashPathEffect(Canvas canvas) {
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);

        Path dashPath1 = new Path();
        Path pp1 = new Path();
        pp1.moveTo(20, 0);
        pp1.lineTo(0, 30);
        pp1.lineTo(40, 30);
        pp1.close();
        dashPath1.addPath(pp1);
        PathEffect pathEffect1 = new PathDashPathEffect(dashPath1, 40, 0, PathDashPathEffect.Style.TRANSLATE);
        paint.setPathEffect(pathEffect1);
        canvas.drawRect(100, 100, 900, 300, paint);

        Path dashPath2 = new Path();
        Path pp2 = new Path();
        pp2.moveTo(20, 0);
        pp2.lineTo(0, 30);
        pp2.lineTo(40, 30);
        pp2.close();
        dashPath2.addPath(pp2);
        PathEffect pathEffect2 = new PathDashPathEffect(dashPath2, 40, 0, PathDashPathEffect.Style.MORPH);
        paint.setPathEffect(pathEffect2);
        canvas.drawRect(100, 500, 900, 700, paint);


        Path dashPath3 = new Path();
        Path pp3 = new Path();
        pp3.moveTo(20, 0);
        pp3.lineTo(0, 30);
        pp3.lineTo(40, 30);
        pp3.close();
        dashPath3.addPath(pp3);
        PathEffect pathEffect3 = new PathDashPathEffect(dashPath3, 40, 0, PathDashPathEffect.Style.ROTATE);
        paint.setPathEffect(pathEffect3);
        canvas.drawRect(100, 900, 900, 1300, paint);

    }

    /**
     * 绘制虚线效果
     *
     * @param canvas
     */
    private void drawDashPathEffect(Canvas canvas) {
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);

        PathEffect pathEffectOne = new DashPathEffect(new float[]{40, 20}, 0);
        paint.setPathEffect(pathEffectOne);
        Path pathOne = new Path();
        pathOne.moveTo(100, 100);
        pathOne.lineTo(1000, 100);
        canvas.drawPath(pathOne, paint);

        Path pathTwo = new Path();
        PathEffect pathEffectTwo = new DashPathEffect(new float[]{100, 20}, 50);  //偏移量 添加 50
        paint.setPathEffect(pathEffectTwo);
        pathTwo.moveTo(100, 300);
        pathTwo.lineTo(1000, 300);
        canvas.drawPath(pathTwo, paint);

        paint.setPathEffect(new DashPathEffect(new float[]{40, 20}, 0));
        canvas.drawCircle(500, 700, 300, paint);
        // drawLine 画不出虚线效果
        canvas.drawLine(50, 1100, 1100, 1100, paint);
    }

    /**
     * 设置拐角路据
     *
     * @param canvas
     */
    private void drawPathEffect(Canvas canvas) {
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);


        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(300, 400);
        path.lineTo(500, 100);
        path.lineTo(700, 400);
        path.lineTo(900, 100);
        path.lineTo(1100, 400);
        canvas.drawPath(path, paint);

        PathEffect pathEffect = new CornerPathEffect(20);
        paint.setPathEffect(pathEffect);
        path.reset();
        path.moveTo(100, 500);
        path.lineTo(300, 800);
        path.lineTo(500, 500);
        path.lineTo(700, 800);
        path.lineTo(900, 500);
        path.lineTo(1100, 800);
        canvas.drawPath(path, paint);

        PathEffect pathEffect1 = new CornerPathEffect(100);
        paint.setPathEffect(pathEffect1);
        path.reset();
        path.moveTo(0, 900);
        path.lineTo(200, 1200);
        path.lineTo(400, 900);
        path.lineTo(600, 1200);
        path.lineTo(800, 900);
        path.lineTo(1000, 1200);
        canvas.drawPath(path, paint);

        paint.setColor(Color.RED);
        paint.setAlpha(100);
        PathEffect pathEffect2 = new CornerPathEffect(50);
        paint.setPathEffect(pathEffect2);
        path.reset();
        path.moveTo(100, 900);
        path.lineTo(300, 1200);
        path.lineTo(500, 900);
        path.lineTo(700, 1200);
        path.lineTo(900, 900);
        path.lineTo(1100, 1200);
        canvas.drawPath(path, paint);

        paint.setColor(Color.YELLOW);
        paint.setAlpha(100);
        PathEffect pathEffect3 = new CornerPathEffect(20);
        paint.setPathEffect(pathEffect3);
        path.reset();
        path.moveTo(200, 900);
        path.lineTo(400, 1200);
        path.lineTo(600, 900);
        path.lineTo(800, 1200);
        path.lineTo(1000, 900);
        path.lineTo(1200, 1200);
        canvas.drawPath(path, paint);
    }

    /**
     * 设置线头
     *
     * @param canvas
     */
    private void drawCap(Canvas canvas) {
        paint.setStrokeWidth(100);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(200, 300, 600, 300, paint);

        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(200, 500, 600, 500, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(200, 700, 600, 700, paint);
    }

    /**
     * 设置线段连接模式
     *
     * @param canvas
     */
    private void drawJoin(Canvas canvas) {
        paint.setStrokeWidth(50);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);

        paint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawRect(100, 100, 400, 400, paint);

        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawRect(500, 100, 900, 400, paint);

        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawRect(100, 500, 400, 800, paint);

        paint.setStrokeJoin(Paint.Join.BEVEL);
        Path path = new Path();
        path.moveTo(500, 500);
        path.lineTo(900, 500);
        path.lineTo(500, 800);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setStrokeJoin(Paint.Join.MITER);
        path.moveTo(100, 900);
        path.lineTo(400, 900);
        path.lineTo(100, 1300);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setStrokeJoin(Paint.Join.ROUND);
        path.moveTo(500, 900);
        path.lineTo(900, 900);
        path.lineTo(500, 1300);
        canvas.drawPath(path, paint);
    }

    /**
     * 设置文字绘制对齐模式
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawCircle(500, 100, 20, paint);
        canvas.drawCircle(500, 300, 20, paint);
        canvas.drawCircle(500, 500, 20, paint);


        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("设置文字 left", 500, 100, textPaint);

        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("设置文字 CENTER", 500, 300, textPaint);

        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("设置文字 RIGHT", 500, 500, textPaint);
    }

    /**
     * 设置路据填充模式
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(100);
        canvas.drawCircle(300, 300, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);
        canvas.drawCircle(800, 300, 200, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAlpha(100);
        canvas.drawCircle(300, 800, 200, paint);


        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(800, 800, 200, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(100);
        canvas.drawCircle(800, 800, 200, paint);
    }

    /**
     * 绘制横竖方格背景
     *
     * @param canvas
     */
    public void drawPg(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 画竖
        for (int i = 100; i < width; i = i + 100) {
            canvas.drawLine(i, 0, i, height, bgPaint);
        }

        // 画横
        for (int i = 100; i < height; i = i + 100) {
            canvas.drawLine(0, i, width, i, bgPaint);
        }
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }
}
