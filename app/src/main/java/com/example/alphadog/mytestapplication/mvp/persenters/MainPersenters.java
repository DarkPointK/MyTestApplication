package com.example.alphadog.mytestapplication.mvp.persenters;


import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alpha Dog on 2016/12/1.
 */

public class MainPersenters implements MainPersentersInterface{
    private List<String> tags;
    private float oldX, oldY;
    private float mLastX, mLastY;

    public MainPersenters() {
        tags = new ArrayList<>();
    }

    public Boolean showNewFragment(FragmentManager fragmentManager, String tag) {

        for (String t : tags) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(t)).commit();
        }
        if (tags.contains(tag)) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(tag)).commit();
        }
        return !tags.contains(tag);
    }

    public void clearTag() {
        tags.clear();
    }

    public void addTag(String tag){
        tags.add(tag);
    }

    public Boolean onFabTouch(DisplayMetrics dm, FloatingActionButton fab,View v, MotionEvent event){
        float rawY = event.getRawY();
        float rawX = event.getRawX();
        float delayX;
        float delayY;
        float translationX;
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = rawX;
                oldY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                if (rawX >= (widthPixels / 2)) {
                    delayX = 0;
                } else {
                    delayX = v.getWidth() - widthPixels;
                }

                if (rawY <= v.getWidth()) {
                    delayY = v.getWidth() - v.getTop();
                    ViewHelper.setTranslationY(v, delayY);

                } else if (rawY >= heightPixels - v.getWidth()) {
                    delayY = heightPixels - v.getBottom() - v.getWidth();
                    ViewHelper.setTranslationY(v, delayY);
                }

                ViewHelper.setTranslationX(v, delayX);
                if (Math.abs(oldX - rawX) <= 1 && Math.abs(oldY - rawY) <= 1) {
                    fab.callOnClick();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                delayX = rawX - mLastX;
                delayY = rawY - mLastY;
                translationX = ViewHelper.getTranslationX(v);
                ViewHelper.setTranslationX(v, translationX + delayX);
                ViewHelper.setTranslationY(v, ViewHelper.getTranslationY(v) + delayY);
                break;
            default:
                break;
        }
        mLastY = rawY;
        mLastX = rawX;
        return true;

    }
}
