package com.example.alphadog.mytestapplication.dagger.component;

import com.example.alphadog.mytestapplication.dagger.AppScope;
import com.example.alphadog.mytestapplication.mvp.view.MainActivity;
import com.example.alphadog.mytestapplication.ui.MyAnimView;

import dagger.Component;

/**
 * Created by Alpha Dog on 2016/11/29.
 */
@AppScope
@Component(dependencies = AppComponent.class)
public interface MyAnimViewFramgComponent {
    void inject(MainActivity activity);
    void inject(MyAnimView view);

//    将下面方法通过接口暴露出去，依赖此Component的组件得以使用此方法
//    Point getPoint();
}
