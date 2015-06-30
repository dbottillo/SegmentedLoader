package com.danielebottillo.segmentedloader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class SegmentedLoader extends View {

    public int startColor;
    public int middleColor;
    public int endColor;

    private static final long FRAME_DURATION = 1000 / 60;

    private int lastColor = 0;
    private int currentColor;
    private int numberOfSteps;
    private int speed;

    private int w, h;

    private int currentPath = 0;
    private int currentStep = 0;

    private boolean isAnimated = false;
    private boolean reversed = false;
    private boolean growing = true;
    private boolean fillOnStart = false;

    Paint paint = new Paint();
    Path path = new Path();

    ArrayList<Segment> segments = new ArrayList<Segment>();

    public SegmentedLoader(Context context) {
        this(context, null);
    }

    public SegmentedLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentedLoader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedLoader, defStyle, 0);
        startColor = attrArray.getColor(R.styleable.SegmentedLoader_start_color, Color.parseColor("#223049"));
        middleColor = attrArray.getColor(R.styleable.SegmentedLoader_middle_color, 0);
        endColor = attrArray.getColor(R.styleable.SegmentedLoader_end_color, Color.parseColor("#00A4DF"));
        reversed = attrArray.getBoolean(R.styleable.SegmentedLoader_reversed, false);
        speed = attrArray.getInt(R.styleable.SegmentedLoader_speed, 1000);
        fillOnStart = attrArray.getBoolean(R.styleable.SegmentedLoader_fill_on_start, false);
        attrArray.recycle();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        currentColor = startColor;
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
        invalidate();
    }

    public SegmentedLoader fillOnStart(){
        fillOnStart = true;
        return this;
    }

    public void hide() {
        stopAnimation();
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
        startAnimation();
    }

    public void setStartColor(int color) {
        startColor = color;
        invalidate();
    }

    public void setMiddleColor(int color) {
        middleColor = color;
        invalidate();
    }

    public void setEndColor(int color) {
        endColor = color;
        invalidate();
    }

    public SegmentedLoader setReversed() {
        reversed = true;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        w = getWidth();
        h = getHeight();

        if (!isAnimated) {
            // if it's not animated, then we can draw all the segments at the same time
            paint.setColor(startColor);
            for (Segment segment : segments) {
                drawPathOnCanvasWithOffset(canvas, segment, 1.0f);
            }
            super.onDraw(canvas);
            return;
        }

        if (lastColor != 0) {
            // after the first draw of all segments, we can to keep the old one visible under the new one
            if (growing) {
                paint.setColor(lastColor);
            } else {
                paint.setColor(lastColor);
            }
            for (Segment segment : segments) {
                drawPathOnCanvasWithOffset(canvas, segment, 1.0f);
            }
        }

        float offset = (float) currentStep / (float) numberOfSteps;

        paint.setColor(currentColor);

        if (growing) {
            for (int i = 0; i < currentPath; i++) {
                drawPathOnCanvasWithOffset(canvas, segments.get(i), 1.0f);
            }
            drawPathOnCanvasWithOffset(canvas, segments.get(currentPath), offset);
        } else {
            int currentPathInverted = segments.size() - currentPath - 1;
            for (int i = segments.size() - 1; i > currentPathInverted; i--) {
                drawPathOnCanvasWithOffset(canvas, segments.get(i), 1.0f);
            }
            drawPathOnCanvasWithOffset(canvas, segments.get(currentPathInverted), offset, true);
        }
    }

    private void updateUI() {
        currentStep++;
        if (currentStep == numberOfSteps) {
            currentStep = 0;
            currentPath++;
            if (currentPath == segments.size()) {
                currentPath = 0;
                lastColor = currentColor;
                if (currentColor == startColor) {
                    if (middleColor != 0) {
                        currentColor = middleColor;
                    } else {
                        currentColor = endColor;
                    }
                } else if (currentColor == middleColor) {
                    currentColor = endColor;
                } else {
                    currentColor = startColor;
                }
                if (reversed) {
                    growing = !growing;
                }
            }
        }
        invalidate();
    }

    Handler mHandler = new Handler();
    Runnable mTick = new Runnable() {
        public void run() {
            updateUI();
            mHandler.postDelayed(mTick, FRAME_DURATION);
        }
    };

    public SegmentedLoader setSpeed(int ms) {
        speed = ms;
        return this;
    }

    public void startAnimation() {
        if (!isAnimated) {
            isAnimated = true;
            if (fillOnStart){
                lastColor = endColor;
            }
            currentStep = 0;
            int segmentSpeed = speed / segments.size();
            numberOfSteps = (int) ((segmentSpeed / FRAME_DURATION) / segments.size());
            mHandler.removeCallbacks(mTick);
            mHandler.post(mTick);
        }
    }

    public void stopAnimation() {
        isAnimated = false;
        currentStep = 0;
        mHandler.removeCallbacks(mTick);
        lastColor = 0;
        invalidate();
    }

    private void drawGrowingLine(Path path, PointF start, PointF end, float offset) {
        float targetX = w * (start.x + (end.x - start.x) * offset);
        float targetY = h * (start.y + (end.y - start.y) * offset);
        path.lineTo(targetX, targetY);
    }

    private void drawPathOnCanvasWithOffset(Canvas canvas, Segment segment, float offset) {
        drawPathOnCanvasWithOffset(canvas, segment, offset, false);
    }

    private void drawPathOnCanvasWithOffset(Canvas canvas, Segment segment, float offset, boolean inverted) {
        path.reset();
        if (inverted) {
            path.moveTo(w * segment.getEndLeft().x, h * segment.getEndLeft().y);
            path.lineTo(w * segment.getEndRight().x, h * segment.getEndRight().y);
            drawGrowingLine(path, segment.getEndRight(), segment.getStartRight(), offset);
            drawGrowingLine(path, segment.getEndLeft(), segment.getStartLeft(), offset);
            path.lineTo(w * segment.getEndLeft().x, h * segment.getEndLeft().y);
        } else {
            path.moveTo(w * segment.getStartLeft().x, h * segment.getStartLeft().y);
            path.lineTo(w * segment.getStartRight().x, h * segment.getStartRight().y);
            drawGrowingLine(path, segment.getStartRight(), segment.getEndRight(), offset);
            drawGrowingLine(path, segment.getStartLeft(), segment.getEndLeft(), offset);
            path.lineTo(w * segment.getStartLeft().x, h * segment.getStartLeft().y);
        }
        path.close();
        canvas.drawPath(path, paint);
    }

}
