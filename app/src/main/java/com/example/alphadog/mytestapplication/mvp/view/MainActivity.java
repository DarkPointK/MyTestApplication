package com.example.alphadog.mytestapplication.mvp.view;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.fragment.MyTextViewFragment;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private float oldX, oldY;
    private float mLastX, mLastY;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private List<String> tags;
    private String[] strTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strTags = new String[]{"MyTextViewFragment", "MainActivityFragment"};
        getWindow().addFlags(FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {    //5.0以上全透明
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN       //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().clearFlags(FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);     //重绘状态栏背景
            getWindow().setStatusBarColor(Color.TRANSPARENT);       //状态栏透明
        }

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mNavigationView = (NavigationView) findViewById(R.id.nv);

        tags = new ArrayList<>();
//        tags.add(strTags[1]);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
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
        int id = item.getItemId();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (id) {
            case R.id.my_textview:
                if (showNewFragment(strTags[0])) {
                    tags.add(strTags[0]);
                    transaction.add(R.id.fragment, new MyTextViewFragment(), strTags[0]).commit();
                }
//            transaction.addToBackStack(null);
                break;
            case R.id.my_anim:
//                showNewFragment("MainActivityFragment");
                showNewFragment(strTags[1]);
//                getFragmentManager().beginTransaction().show(getFragmentManager().findFragmentByTag(strTags[1])).commit();
//                    tags.add("MainActivityFragment");
//                    transaction.add(R.id.fragment, new MainActivityFragment(), "MainActivityFragment").commit();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openOrCloseDrawer();
                }
            });
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    public void openOrCloseDrawer() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawer(mNavigationView);
        } else {
            mDrawerLayout.openDrawer(mNavigationView);
        }
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private Boolean showNewFragment(String tag) {

        for (String t : tags) {
            getFragmentManager().beginTransaction().hide(getFragmentManager().findFragmentByTag(t)).commit();
        }
        if (tags.contains(tag)) {
            getFragmentManager().beginTransaction().show(getFragmentManager().findFragmentByTag(tag)).commit();
        }
        return !tags.contains(tag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tags.clear();
    }
}
