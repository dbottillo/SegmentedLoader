package com.danielebottillo.segmentedloader;

import android.graphics.PointF;

public class Segment {

    PointF startLeft, startRight, endRight, endLeft;

    int scale = 10;

    public Segment() {

    }

    public Segment setScale(int scale) {
        this.scale = scale;
        return this;
    }

    public Segment setTopLeftPoint(float x, float y) {
        startLeft = new PointF(x, y);
        return this;
    }

    public Segment setTopLeftPoint(int x, int y) {
        startLeft = new PointF((float) x / scale, (float) y / scale);
        return this;
    }

    public Segment setTopRightPoint(float x, float y) {
        startRight = new PointF(x, y);
        return this;
    }

    public Segment setTopRightPoint(int x, int y) {
        startRight = new PointF((float) x / scale, (float) y / scale);
        return this;
    }

    public Segment setBottomRightPoint(float x, float y) {
        endRight = new PointF(x, y);
        return this;
    }

    public Segment setBottomRightPoint(int x, int y) {
        endRight = new PointF((float) x / scale, (float) y / scale);
        return this;
    }

    public Segment setBottomLeftPoint(float x, float y) {
        endLeft = new PointF(x, y);
        return this;
    }

    public Segment setBottomLeftPoint(int x, int y) {
        endLeft = new PointF((float) x / scale, (float) y / scale);
        return this;
    }

    public PointF getStartLeft() {
        return startLeft;
    }

    public PointF getStartRight() {
        return startRight;
    }

    public PointF getEndRight() {
        return endRight;
    }

    public PointF getEndLeft() {
        return endLeft;
    }
}
