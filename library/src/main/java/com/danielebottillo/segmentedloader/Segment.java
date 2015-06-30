package com.danielebottillo.segmentedloader;

import android.graphics.PointF;

public class Segment {

    PointF startLeft, startRight, endRight, endLeft;

    int gridSize = 10;

    public Segment() {
    }

    public Segment(int gridSize) {
        this.gridSize = gridSize;
    }

    public Segment(int gridSize, int startleftX, int startLeftY, int startRightX, int startRightY, int endLeftX, int endLeftY, int endRightX, int endRightY) {
        this.gridSize = gridSize;
        startLeft = new PointF((float) startleftX / gridSize, (float) startLeftY / gridSize);
        startRight = new PointF((float) startRightX / gridSize, (float) startRightY / gridSize);
        endRight = new PointF((float) endLeftX / gridSize, (float) endLeftY / gridSize);
        endLeft = new PointF((float) endRightX / gridSize, (float) endRightY / gridSize);
    }

    public Segment(float startleftX, float startLeftY, float startRightX, float startRightY, float endLeftX, float endLeftY, float endRightX, float endRightY) {
        startLeft = new PointF(startleftX, startLeftY);
        startRight = new PointF(startRightX, startRightY);
        endRight = new PointF(endLeftX, endLeftY);
        endLeft = new PointF(endRightX, endRightY);
    }

    public Segment setGridSize(int gridSize) {
        this.gridSize = gridSize;
        return this;
    }

    public Segment setStartLeftPoint(float x, float y) {
        startLeft = new PointF(x, y);
        return this;
    }

    public Segment setStartLeftPoint(int x, int y) {
        startLeft = new PointF((float) x / gridSize, (float) y / gridSize);
        return this;
    }

    public Segment setStartRightPoint(float x, float y) {
        startRight = new PointF(x, y);
        return this;
    }

    public Segment setStartRightPoint(int x, int y) {
        startRight = new PointF((float) x / gridSize, (float) y / gridSize);
        return this;
    }

    public Segment setEndRightPoint(float x, float y) {
        endRight = new PointF(x, y);
        return this;
    }

    public Segment setEndRightPoint(int x, int y) {
        endRight = new PointF((float) x / gridSize, (float) y / gridSize);
        return this;
    }

    public Segment setEndLeftPoint(float x, float y) {
        endLeft = new PointF(x, y);
        return this;
    }

    public Segment setEndLeftPoint(int x, int y) {
        endLeft = new PointF((float) x / gridSize, (float) y / gridSize);
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
