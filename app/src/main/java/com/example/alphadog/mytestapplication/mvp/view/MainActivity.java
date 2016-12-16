package com.example.alphadog.mytestapplication.mvp.view;

import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.jni.HelloJni;
import com.example.alphadog.mytestapplication.mvp.persenters.MainPersenters;
import com.example.alphadog.mytestapplication.mvp.view.fragment.DataBaseContortFragment;
import com.example.alphadog.mytestapplication.mvp.view.fragment.MainActivityFragment;
import com.example.alphadog.mytestapplication.mvp.view.fragment.MyTextViewFragment;
import com.example.alphadog.mytestapplication.mvp.view.fragment.RecycleFragment;

public class MainActivity extends BaseActivity {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private MenuItem searchItem;
    private boolean search = false;
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

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, new MainActivityFragment(), strTags[3]).addToBackStack(null).commit();

//        JNI调试
        Toast.makeText(this, "" + HelloJni.helloJni() + HelloJni.addJni(1, 2), Toast.LENGTH_SHORT).show();

//        在setSupportActionBar前setTitle
        if (toolbar != null) {
            toolbar.setTitle(strTags[3]);
        }
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
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchItem = menu.findItem(R.id.action_search);

        searchItem.setVisible(search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ComponentName componentName = getComponentName();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        MenuItemCompat.setOnActionExpandListener(searchItem, mPersenters);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        收起搜索框
        toolbar.collapseActionView();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        String title;
        switch (id) {
            case R.id.my_anim:
//                if (mPersenters.showNewFragment(getFragmentManager(), strTags[0])) {
//                    mPersenters.addTag(strTags[0]);
                search = false;
                searchItem.setVisible(false);
                title = strTags[0];
                MainActivityFragment fragment = new MainActivityFragment();
                Bundle b = new Bundle();
                b.putString("type", "ball");
                fragment.setArguments(b);
                transaction.replace(R.id.fragment, fragment, strTags[0]).addToBackStack(null).commit();
//                }
                break;
            case R.id.my_textview:
                search = false;
                searchItem.setVisible(false);
                title = strTags[1];
                transaction.replace(R.id.fragment, new MyTextViewFragment(), strTags[1]).addToBackStack(null).commit();
                break;
            case R.id.recycle:
                search = true;
                searchItem.setVisible(true);
                title = strTags[2];
                transaction.replace(R.id.fragment, new RecycleFragment(), strTags[2]).addToBackStack(null).commit();
                break;
            case R.id.heart:
                search = false;
                searchItem.setVisible(false);
                title = strTags[3];

                transaction.replace(R.id.fragment, new MainActivityFragment(), strTags[2]).addToBackStack(null).commit();
                break;
            case R.id.database:
                search = false;
                searchItem.setVisible(false);
                title = strTags[4];
                transaction.replace(R.id.fragment, new DataBaseContortFragment(), strTags[4]).addToBackStack(null).commit();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        if (toolbar != null) {
            toolbar.setTitle(title);
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
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
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
