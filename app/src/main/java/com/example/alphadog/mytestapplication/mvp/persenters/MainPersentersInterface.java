package com.example.alphadog.mytestapplication.mvp.persenters;

import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Alpha Dog on 2016/12/1.
 */

public interface MainPersentersInterface {
    Boolean showNewFragment(FragmentManager fragmentManager, String tag);
    void clearTag();
    void addTag(String tag);
    Boolean onFabTouch(DisplayMetrics dm, FloatingActionButton fab, View v, MotionEvent event);
}
