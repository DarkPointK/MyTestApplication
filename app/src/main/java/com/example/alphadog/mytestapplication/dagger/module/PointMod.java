package com.example.alphadog.mytestapplication.dagger.module;

import com.example.alphadog.mytestapplication.mvp.module.Point;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alpha Dog on 2016/11/29.
 */
@Module
public class PointMod {
    @Provides
    Point getPoint(){
        return new Point(50f,50f);
    }
}
