package com.example.alphadog.mytestapplication.mvp.view;

import android.os.Bundle;
import android.view.KeyEvent;

import com.example.alphadog.mytestapplication.R;

/**
 * Created by Alpha Dog on 2016/12/13.
 */

public class SearchActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
