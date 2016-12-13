package com.example.alphadog.mytestapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.example.alphadog.mytestapplication.Fruit;
import com.example.alphadog.mytestapplication.IFruitAidlInterface;
import com.example.alphadog.mytestapplication.mvp.persenters.MainPersenters;
import com.example.alphadog.mytestapplication.mvp.view.MainActivity;

import static android.util.Log.d;

/**
 * Created by Alpha Dog on 2016/12/7.
 */

public class AppLifeTimeService extends Service {
    private Messenger mServiceMessage = new Messenger(new ServiceHandler());
    private Messenger mReplyMessnger;
    private long time;
    public static final int GET_TIME = 1;
    private IBinder mIBinder;
    private MyFruitImp mMyFruitImp;
    private long oldTime, nowTime;

    //    IfruitAudlInterface接口的实现
    private class MyFruitImp extends IFruitAidlInterface.Stub {
        @Override
        public String getSomeThing() throws RemoteException {
            return "记得";
        }

        @Override
        public Fruit getFruit() throws RemoteException {
            return new Fruit("苹果", time);
        }

    }

    class ServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                 case GET_TIME:

                    nowTime = System.currentTimeMillis();
//                    算出时间间隔
                    time = (nowTime - oldTime) / 1000;
                    d("123456", nowTime + " : " + oldTime);
//                    取得用于回传的Messenger
                    mReplyMessnger = msg.replyTo;
//                    如果超过5秒，回传该吃水果的信息并break，否则显示运行了几秒
                    if (time >= 5) {
                        try {
                            d("ServiceHandler", "开始返回信息");
                            Message mess = Message.obtain(null, MainPersenters.EAT);
                            mess.obj = time;
//                            将信息与时间间隔回传
                            mReplyMessnger.send(mess);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    Toast.makeText(AppLifeTimeService.this, "程序运行了" + time + "秒", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        d("AppLifeTimeService", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        d("AppLifeTimeService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("AppLifeTimeService", "onBind");
//        第一次进入app时打包进intent的时间
        oldTime = intent.getLongExtra("oldTime", oldTime);
//        上次服务运行结束打包进intent的时间
        time = intent.getLongExtra("time", 0);

//        根据time决定返回什么样的IBind
        if (time >= 5) {
            try {
                d("ServiceHandler", "time1:" + time);
                mMyFruitImp = new MyFruitImp();
                mIBinder = mMyFruitImp;
                d("ServiceHandler", "送出水果:" + mMyFruitImp.getFruit().toString());

//                通知栏
                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Intent newIntent=new Intent(this, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification = new Notification.Builder(this)
                        .setAutoCancel(true)
                        .setContentTitle("提醒")
                        .setContentText("送来水果:" + mMyFruitImp.getFruit().toString())
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(android.R.drawable.ic_input_add)
                        .build();
                notificationManager.notify(1, notification);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mIBinder = mServiceMessage.getBinder();
            Log.d("ServiceHandler", "送出信息" + time);
        }


        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        d("AppLifeTimeService", "onUnbind");
//        返回true的话不销毁服务
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        d("AppLifeTimeService", "onRebind");
    }

    @Override
    public void onDestroy() {
        d("AppLifeTimeService", "onDestroy");
        super.onDestroy();
    }
}
