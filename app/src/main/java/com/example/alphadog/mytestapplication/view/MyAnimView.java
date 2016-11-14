package com.example.alphadog.mytestapplication.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.alphadog.mytestapplication.evaluator.ColorEvaluator;
import com.example.alphadog.mytestapplication.evaluator.PointEvaluator;
import com.example.alphadog.mytestapplication.module.Point;

/**
 * Created by Alpha Dog on 2016/10/10.
 */
public class MyAnimView extends View {

    public static final float RADIUS = 50f;

    private Point currentPoint;

    private Paint mPaint;
    private String color;
    private AnimatorSet animSet;

    public MyAnimView(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() / 2 - getPaddingLeft() - RADIUS, getHeight() / 2 - getPaddingTop() - RADIUS);

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(this, "scaleY", 1f, 2f, 0.5f);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(this, "scaleX", 1f, 2f, 0.5f);

        animSet = new AnimatorSet();
        animSet.play(anim3).with(anim4).after(anim2).after(anim);
        animSet.setDuration(1000);
        animSet.start();
    }
}
