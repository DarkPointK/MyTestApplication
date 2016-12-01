package com.example.alphadog.mytestapplication.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.alphadog.mytestapplication.App;
import com.example.alphadog.mytestapplication.dagger.component.DaggerMyAnimViewFramgComponent;
import com.example.alphadog.mytestapplication.dagger.component.MyAnimViewFramgComponent;
import com.example.alphadog.mytestapplication.evaluator.ColorEvaluator;
import com.example.alphadog.mytestapplication.evaluator.PointEvaluator;
import com.example.alphadog.mytestapplication.mvp.module.Point;

import javax.inject.Inject;

/**
 * Created by Alpha Dog on 2016/10/10.
 */
public class MyAnimView extends View {

    private MyAnimViewFramgComponent mComponent;

    public static final float RADIUS = 50f;
    float scan=1;
    @Inject
    Point currentPoint;

    @Inject
    Point startPoint;
    private Paint mPaint;
    private String color;
    private AnimatorSet animSet;

    public MyAnimView(Context context) {
        super(context);
        if (isInEditMode()) { return; }

        mComponent = DaggerMyAnimViewFramgComponent.builder().appComponent(((App)((Activity)context).getApplication()).getComponent()).build();
        mComponent.inject(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public MyAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) { return; }

        mComponent = DaggerMyAnimViewFramgComponent.builder().appComponent(((App)((Activity)context).getApplication()).getComponent()).build();
        mComponent.inject(this);
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (currentPoint == null) {
//            currentPoint = new Point(RADIUS, RADIUS);
//            drawCircle(canvas);
//            startAnimation();
//        } else {
            drawCircle(canvas);

//        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        Log.d("MainActivity", "RADIUS*scan:" + RADIUS*scan);
        canvas.drawCircle(x, y, RADIUS*scan, mPaint);
    }

    private void startAnimation() {
        Point endPoint = new Point(getWidth() / 2 - getPaddingLeft() , getHeight() / 2 - getPaddingTop());

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });

        ValueAnimator anim3=ValueAnimator.ofFloat(1,2,1);
        anim3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scan=(float)animation.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");

        animSet = new AnimatorSet();
        animSet.play(anim3).after(anim2).after(anim);
        animSet.setDuration(1000);
        animSet.start();

    }
}
