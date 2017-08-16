package com.example.alphadog.mytestapplication.mvp.persenters;


import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.IFruitAidlInterface;
import com.example.alphadog.mytestapplication.R;
import com.example.alphadog.mytestapplication.mvp.view.MainActivity;
import com.example.alphadog.mytestapplication.service.AppLifeTimeService;
import com.nineoldandroids.view.ViewHelper;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;

/**
 * Created by Alpha Dog on 2016/12/1.
 */

public class MainPersenters implements MainPersentersInterface, MenuItemCompat.OnActionExpandListener {
    public static final int EAT = 1;
    private List<String> tags;
    private float oldX, oldY;
    private float mLastX, mLastY;
    private Intent appLifeTimeService;
    private AppLifeTimeServiceConnection mServiceConn = new AppLifeTimeServiceConnection();
    private Messenger message;
    private Messenger replyMess = new Messenger(new ReplyHolder());
    private boolean eatB = false;
    private IFruitAidlInterface mFruitAidlInterface;
    private MainActivity mMainActivity;
    private long time;
    private long oldTime;

    public MainPersenters(MainActivity mainActivity) {
        mMainActivity = mainActivity;
        oldTime = System.currentTimeMillis();

        appLifeTimeService = new Intent(mainActivity, AppLifeTimeService.class);
        appLifeTimeService.putExtra("oldTime", oldTime);
        tags = new ArrayList<>();

        mMainActivity.bindService(appLifeTimeService, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
//        搜索框打开
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
//        搜索框关闭
        return true;
    }

    private class ReplyHolder extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EAT:
//                    接受回应并处理
                    time = (long) msg.obj;
                    d("ServiceHandler", "time2:" + time);

                    eatB = true;
//                    打包上次的时间间隔并传递intent重绑服务
                    appLifeTimeService.putExtra("time", time);
                    d("ServiceHandler", "接收到返回信息");
                    mMainActivity.unbindService(mServiceConn);
                    mMainActivity.bindService(appLifeTimeService, mServiceConn, Context.BIND_AUTO_CREATE);
                    break;
            }
        }
    }

    private class AppLifeTimeServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (!eatB) {
                d("ServiceHandler", "处理信息");
                message = new Messenger(service);
            } else {
//                接受回应后绑定成功将传回IFruitAidlInterface的实现，在此处理
                mFruitAidlInterface = IFruitAidlInterface.Stub.asInterface(service);
                try {
                    String s = mFruitAidlInterface.getSomeThing();
                    String s1 = mFruitAidlInterface.getFruit().toString();
//                    Toast.makeText(mMainActivity, s + s1, Toast.LENGTH_SHORT).show();
                    if (mMainActivity.findViewById(R.id.fab) != null)
                        Snackbar.make(mMainActivity.findViewById(R.id.fab), s + s1, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    d("ServiceHandler", "处理水果 " + s + s1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public Boolean showNewFragment(FragmentManager fragmentManager, String tag) {

        for (String t : tags) {
            fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(t)).commit();
        }
        if (tags.contains(tag)) {
            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag(tag)).commit();
        }
        return !tags.contains(tag);
    }

    public void clearTag() {
        tags.clear();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public Boolean onFabTouch(DisplayMetrics dm, FloatingActionButton fab, View v, MotionEvent event) {
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

    public void showTime() {
        try {
            Message mess = Message.obtain(null, AppLifeTimeService.GET_TIME);
            mess.replyTo = replyMess;
//            向服务发送信息并定义一个待回应的Messenger
            message.send(mess);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void destoryService(MainActivity mainActivity) {
        mainActivity.unbindService(mServiceConn);
        mainActivity.stopService(appLifeTimeService);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mMainActivity, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mMainActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
