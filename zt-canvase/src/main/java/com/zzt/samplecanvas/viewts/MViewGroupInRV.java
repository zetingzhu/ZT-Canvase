package com.zzt.samplecanvas.viewts;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author: zeting
 * @date: 2024/4/22
 */
public class MViewGroupInRV extends ViewGroup {
    public MViewGroupInRV(Context context) {
        super(context);
    }

    public MViewGroupInRV(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MViewGroupInRV(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount >= 2) {
            View childAt0 = getChildAt(0);

            View childAt1 = getChildAt(1);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
