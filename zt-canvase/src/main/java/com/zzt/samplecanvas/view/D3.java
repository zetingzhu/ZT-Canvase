package com.zzt.samplecanvas.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zzt.samplecanvas.R;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
public class D3 extends View {
    public D3(Context context) {
        super(context);
    }

    public D3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public D3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        if (rawBitmap != null) {
            Bitmap bitmap = getRoundCornerBitmap(rawBitmap, 50);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }
    }

    /**
     * @param bitmap 原图
     * @param pixels 圆角大小
     * @return
     */
    public Bitmap getRoundCornerBitmap(Bitmap bitmap, float pixels) {
//获取bitmap的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap cornerBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(cornerBitmap);
        paint.setAntiAlias(true);

        canvas.drawRoundRect(new RectF(0, 0, width, height), pixels, pixels, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, new RectF(0, 0, width, height), paint);

//绘制边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.GREEN);
        canvas.drawRoundRect(new RectF(0, 0, width, height), pixels, pixels, paint);

        return cornerBitmap;

    }
}
