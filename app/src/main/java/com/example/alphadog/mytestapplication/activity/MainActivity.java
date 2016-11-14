package com.example.alphadog.mytestapplication.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.fragment.MyTextViewFragment;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private float oldX, oldY;
    private float mLastX, mLastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.my_textview) {
            MyTextViewFragment newFragment = new MyTextViewFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, newFragment);
            transaction.addToBackStack(null);
            //提交修改
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initListener() {
        final DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
        });
    }

}
