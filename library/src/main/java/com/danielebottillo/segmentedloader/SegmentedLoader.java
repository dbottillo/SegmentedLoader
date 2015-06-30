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

    public int firstColor;
    public int secondColor;

    private static final long FRAME_DURATION = 1000 / 60;

    private int lastColor = 0;
    private int currentColor;

    private int w, h;

    private int currentPath = 0;
    private int currentStep = 0;

    private boolean isAnimated = false;
    private boolean reverted = false;
    private boolean growing = true;

    Paint paint = new Paint();
    Path path = new Path();

    ArrayList<Segment> segments = new ArrayList<Segment>();
    int numberOfSteps;


    public SegmentedLoader(Context context) {
        this(context, null);
    }

    public SegmentedLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SegmentedLoader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedLoader, defStyle, 0);
        boolean animateOnCreation = attrArray.getBoolean(R.styleable.SegmentedLoader_animated, false);
        firstColor = attrArray.getColor(R.styleable.SegmentedLoader_loader_color, Color.parseColor("#223049"));
        secondColor = attrArray.getColor(R.styleable.SegmentedLoader_secondary_color, Color.parseColor("#00A4DF"));
        lastColor = secondColor;
        attrArray.recycle();

        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        currentColor = firstColor;
        /*if (animateOnCreation) {
            startAnimation();
        }*/
    }

    public void addSegment(Segment segment) {
        segments.add(segment);
        invalidate();
    }

    public void hide() {
        stopAnimation();
        setVisibility(View.GONE);
    }

    public void show() {
        setVisibility(View.VISIBLE);
        startAnimation();
    }

    public void show(CharSequence message) {
        // TODO: this method should be removed after ACH of new app
        show();
    }

    public void setColor(int color) {
        firstColor = color;
        invalidate();
    }

    public SegmentedLoader setReverted() {
        reverted = true;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        w = getWidth();
        h = getHeight();

        if (!isAnimated) {
            // if it's not animated, then we can draw all the segments at the same time
            paint.setColor(firstColor);
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
                currentColor = lastColor == firstColor ? secondColor : firstColor;
                if (reverted) {
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

    int segmentSpeed = 1000; // default is 1000 ms

    public SegmentedLoader setSegmentSpeed(int ms) {
        segmentSpeed = ms;
        return this;
    }

    public SegmentedLoader setSpeed(int ms) {
        segmentSpeed = ms / segments.size();
        return this;
    }

    public void startAnimation() {
        if (!isAnimated) {
            isAnimated = true;
            currentStep = 0;
            numberOfSteps = (int) ((segmentSpeed / FRAME_DURATION) / segments.size());
            /*log(segmentSpeed + " - " + FRAME_DURATION + " - " + segments.size());
            log(segmentSpeed / FRAME_DURATION + " - " + numberOfSteps);*/
            mHandler.removeCallbacks(mTick);
            mHandler.post(mTick);
        }
    }

    private void stopAnimation() {
        isAnimated = false;
        currentStep = 0;
        mHandler.removeCallbacks(mTick);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
    }

}
