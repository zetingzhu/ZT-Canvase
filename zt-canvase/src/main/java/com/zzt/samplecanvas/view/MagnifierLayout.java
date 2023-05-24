package com.zzt.samplecanvas.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2023/2/13
 */
public class MagnifierLayout extends FrameLayout {
    private Bitmap mBitmap;
    private Paint mPaintShadow, mPaintBitmap;
    private long mShowTime = 0;//用于判断显示放大镜的时间
    private boolean mIsShowMagnifier = false;//是否显示放大镜
    private Path mPath;//用于裁剪画布的路径
    private float mShowMagnifierX = 0;//显示放大镜的X坐标
    private float mShowMagnifierY = 0;//显示放大镜的Y坐标
    private float mMagnifierRadius = 200;//放大镜的半径
    private float mScaleRate = 3f;//放大比例
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mIsShowMagnifier = true;
            postInvalidate();
        }
    };

    public MagnifierLayout(@NonNull Context context) {
        this(context, null);
    }

    public MagnifierLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MagnifierLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //绘制放大镜边缘投影的画笔
        mPaintShadow = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置放大镜边缘的投影
        mPaintShadow.setShadowLayer(20, 6, 6, Color.BLACK);
        //绘制Bitmap的画笔
        mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPath = new Path();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mShowTime = System.currentTimeMillis();
                mShowMagnifierX = event.getX() + mMagnifierRadius;
                mShowMagnifierY = event.getY() - mMagnifierRadius;
                //如果持续按超过一秒就显示放大镜
                startShowMagnifierDelay();
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸时间大于1秒才显示
                if (System.currentTimeMillis() - mShowTime >= 1000) {
                    stopShowMagnifier();
                    mIsShowMagnifier = true;
                    mShowMagnifierX = event.getX() + mMagnifierRadius;
                    mShowMagnifierY = event.getY() - mMagnifierRadius;
                    //重绘
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                stopShowMagnifier();
                mIsShowMagnifier = false;
                postInvalidate();
                break;
        }
        return true;
    }

    private void startShowMagnifierDelay() {
        stopShowMagnifier();
        mHandler.postDelayed(mRunnable, 1000);
    }

    private void stopShowMagnifier() {
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mIsShowMagnifier) {
            //创建整个布局内容的Bitmap
            mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas mCanvas = new Canvas(mBitmap);
            //将当前布局的内容绘制在Bitmap上
            super.dispatchDraw(mCanvas);
            //将Bitmap绘制出来(否则页面是空白，因为上面是用我们自定义的Canvas进行绘制的，因此我们还需要用系统的这个进行绘制一遍)
            canvas.drawBitmap(mBitmap, 0, 0, mPaintBitmap);
            //绘制出放大镜边缘的投影
            canvas.drawCircle(mShowMagnifierX, mShowMagnifierY, mMagnifierRadius, mPaintShadow);
            canvas.save();
            //计算出要裁剪画布的圆形路径
            mPath.reset();
            mPath.addCircle(mShowMagnifierX, mShowMagnifierY, mMagnifierRadius, Path.Direction.CW);
            //将圆形路径的画布裁剪出来，这样绘制的Bitmap就是圆形的
            canvas.clipPath(mPath);
//            //绘制当前布局的背景颜色，否则放大镜显示的背景会是黑色
//            getBackground().draw(canvas);
            //根据缩放的比例计算出裁剪的Bitmap的最小宽高
            float magnifierWidthAndHeight = mMagnifierRadius * 2 / mScaleRate;
            //计算出该裁剪的区域(就是使手指所在的点在要裁剪的Bitmap的中央)，并进行边界值处理(开始裁剪的X点不能小于0和大于Bitmap的宽度，并且X点的位置加上要裁剪的宽度不能大于Bitmap的宽度，Y点也是一样)
            int cutX = Math.max((int) (mShowMagnifierX - mMagnifierRadius - magnifierWidthAndHeight / 2), 0);
            int cutY = Math.min(Math.max((int) (mShowMagnifierY + mMagnifierRadius - magnifierWidthAndHeight / 2), 0), mBitmap.getHeight());
            //适配边界值
            int cutWidth = magnifierWidthAndHeight + cutX > mBitmap.getWidth() ? mBitmap.getWidth() - cutX : (int) magnifierWidthAndHeight;
            int cutHeight = magnifierWidthAndHeight + cutY > mBitmap.getHeight() ? mBitmap.getHeight() - cutY : (int) magnifierWidthAndHeight;
            mBitmap = Bitmap.createBitmap(mBitmap, cutX, cutY, cutWidth, cutHeight);
            //将裁剪出来的区域放大
            mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (mBitmap.getWidth() * mScaleRate), (int) (mBitmap.getHeight() * mScaleRate), true);
            //绘制放大后的Bitmap，由于Bitmap的缩放是从左上点开始的因此还要根据缩放的比例进行相应的偏移展示
            canvas.drawBitmap(mBitmap, mShowMagnifierX - mMagnifierRadius, mShowMagnifierY - mMagnifierRadius, mPaintBitmap);
            canvas.restore();
            mBitmap.recycle();
            mBitmap = null;
        } else {
            super.dispatchDraw(canvas);
        }
    }
}
