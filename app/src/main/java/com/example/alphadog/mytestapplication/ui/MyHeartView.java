package com.example.alphadog.mytestapplication.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alpha Dog on 2016/10/10.
 */
public class MyHeartView extends View {

    private int offsetX, offsetY;

    private Paint paint;
    private boolean isDrawing = false;
    private float angle = 10L;
    private Path path;

    public MyHeartView(Context context) {
        this(context, null);
    }

    public MyHeartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();

    }

    private Point getHeartPoint(float angle) {
        /**
         * 心形曲线
         * x=16×sin3α
         * y=13×cosα−5×cos2α−2×cos3α−cos4α
         */
        float t = (float) (angle / Math.PI);
        float x = (float) (30 * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-30 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x / 2, offsetY + (int) y / 2);
    }

    private void initData() {
        offsetX = getWidth() / 2;
        offsetY = getHeight() / 2 - 55;
    }


    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10.0f);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initData();
        Point p = getHeartPoint(angle);
        if (!isDrawing) {
            path.moveTo(p.x, p.y);
        }
        path.lineTo(p.x, p.y);
        canvas.drawPath(path, paint);

        onDrawNewThread(canvas);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void onDrawNewThread(final Canvas canvas) {
        new Thread() {
            @Override
            public void run() {
                if (isDrawing) return;
                isDrawing = true;

                while (angle < 30) {
                    angle = angle + 0.02f;
                    postInvalidate();
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (angle >= 29) {
                        Path path = new Path();
                        paint.setStyle(Paint.Style.FILL);
                        Point d = getHeartPoint(angle);
                            path.moveTo(d.x, d.y);
                        for(int a=0;a<30;a++) {
                            path.lineTo(d.x, d.y);
                            canvas.drawPath(path, paint);
                        }
                        mHandler.sendMessage(Message.obtain(null,1));
                    }
                }
            }
        }.start();

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ObjectAnimator.ofFloat(MyHeartView.this, "scaleX", 1, 1.5f).setDuration(1000).start();
                    ObjectAnimator.ofFloat(MyHeartView.this, "scaleY", 1, 1.5f).setDuration(1000).start();

                    ObjectAnimator.ofFloat(MyHeartView.this, "alpha", 1, 0f).setDuration(1000).start();
                    break;
            }
        }
    };
}
