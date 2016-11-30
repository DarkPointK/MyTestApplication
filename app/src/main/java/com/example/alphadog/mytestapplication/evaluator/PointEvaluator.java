package com.example.alphadog.mytestapplication.evaluator;

import android.animation.TypeEvaluator;

import com.example.alphadog.mytestapplication.mvp.module.Point;

/**
 * Created by Alpha Dog on 2016/10/10.
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }

}
