package com.example.alphadog.mytestapplication.mvp.view;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.mvp.persenters.MainPersenters;
import com.example.alphadog.mytestapplication.mvp.view.fragment.MyTextViewFragment;
import com.example.alphadog.mytestapplication.mvp.view.fragment.RecycleFragment;

public class MainActivity extends BaseActivity {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String[] strTags;
    private MainPersenters mPersenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strTags = getResources().getStringArray(R.array.options_item);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mNavigationView = (NavigationView) findViewById(R.id.nv);
        mPersenters = new MainPersenters(this);

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
            case R.id.recycle:
                Toast.makeText(this, "正在开发，敬请期待", Toast.LENGTH_SHORT).show();
                if (mPersenters.showNewFragment(getFragmentManager(), strTags[2])) {
                    mPersenters.addTag(strTags[2]);
                    transaction.add(R.id.fragment, new RecycleFragment(), strTags[2]).addToBackStack(null).commit();
                }
                break;
            case R.id.my_textview:
                if (mPersenters.showNewFragment(getFragmentManager(), strTags[1])) {
                    mPersenters.addTag(strTags[1]);
                    transaction.add(R.id.fragment, new MyTextViewFragment(), strTags[1]).addToBackStack(null).commit();
                }
                break;
            case R.id.my_anim:
                mPersenters.showNewFragment(getFragmentManager(), strTags[1]);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initListener() {

        final DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenters.showTime();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mPersenters.onFabTouch(dm, fab, v, event);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除tag
        mPersenters.clearTag();
        mPersenters.destoryService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}