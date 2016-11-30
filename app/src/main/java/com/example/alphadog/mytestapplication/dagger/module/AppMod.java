package com.example.alphadog.mytestapplication.dagger.module;

import android.view.View;

import com.example.alphadog.mytestapplication.mvp.module.Point;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alpha Dog on 2016/11/29.
 */
@Module
public class AppMod {

    public AppMod(){
    }
    public AppMod( View view){
    }

    @Provides
    @Singleton
    Point getPoint(){
        return new Point(50f,50f);
    }
}
