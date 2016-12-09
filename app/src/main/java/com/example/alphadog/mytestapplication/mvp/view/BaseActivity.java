package com.example.alphadog.mytestapplication.mvp.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

/**
 * Created by Alpha Dog on 2016/12/1.
 */

public class BaseActivity extends AppCompatActivity {
    private long mLastClickTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //5.0以上全透明
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN       //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().clearFlags(FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);     //重绘状态栏背景
            getWindow().setStatusBarColor(Color.TRANSPARENT);       //状态栏透明
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            long now = System.currentTimeMillis();
            if (now - mLastClickTime > 3000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                mLastClickTime = now;
            } else {
                System.exit(0);
//                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
