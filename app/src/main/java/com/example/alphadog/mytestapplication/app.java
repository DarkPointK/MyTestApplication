package com.example.alphadog.mytestapplication;

import android.app.Application;

import com.example.alphadog.mytestapplication.dagger.component.AppComponent;
import com.example.alphadog.mytestapplication.dagger.component.DaggerAppComponent;
import com.example.alphadog.mytestapplication.dagger.module.AppMod;

/**
 * Created by Alpha Dog on 2016/11/29.
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent= DaggerAppComponent.builder().appMod(new AppMod()).build();
    }

    public AppComponent getComponent() {
        return mAppComponent;
    }
}
