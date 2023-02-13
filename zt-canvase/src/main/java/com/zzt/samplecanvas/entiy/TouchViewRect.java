package com.zzt.samplecanvas.entiy;

import android.graphics.Rect;

import com.zzt.samplecanvas.view.D5;

/**
 * @author: zeting
 * @date: 2021/7/15
 */
public class TouchViewRect {
    String label;
    D5 touchView;
    Rect touchRect;
    int startX;
    int startY;

    public TouchViewRect(String label, D5 touchView) {
        this.label = label;
        this.touchView = touchView;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public D5 getTouchView() {
        return touchView;
    }

    public void setTouchView(D5 touchView) {
        this.touchView = touchView;
    }

    public Rect getTouchRect() {
        return touchRect;
    }

    public void setTouchRect(Rect touchRect) {
        this.touchRect = touchRect;
    }
}
