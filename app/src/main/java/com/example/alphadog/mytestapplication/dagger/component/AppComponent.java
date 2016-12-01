package com.example.alphadog.mytestapplication.dagger.component;

import com.example.alphadog.mytestapplication.dagger.module.AppMod;
import com.example.alphadog.mytestapplication.mvp.module.Point;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Alpha Dog on 2016/11/29.
 */

@Singleton
@Component(modules = AppMod.class)
public interface AppComponent {
    //    将下面方法通过接口暴露出去，依赖此Component的组件得以使用此方法
    Point getPoint();
}
