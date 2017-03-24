package com.shangbb.studysample.sample.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shangbb.studysample.util.LogUtils;

/**
 * @Fuction: 服务端，一个子进程服务 Messenger实现跨进成通信,服务端处理客户端的消息是串行的,必须一个一个来处理,所以如果是并发量比较大的时候,
 * 通过Messager来通信就不太适合了
 * @Author: BBShang
 * @Date: 2017/3/24 11:07
 */

public class KMessengerService extends Service {

    public static final String TAG = "KMessengerService";

    public static final int MSG_FROM_CLIENT_TO_SERVER = 1;
    public static final int MSG_FROM_SERVER_TO_CLIENT = 2;

    private Handler mHandler = new ServerHandler();

    private Messenger mMessenger = new Messenger(mHandler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        LogUtils.i(TAG, "onCreat");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.i(TAG, "onDestroy");
        super.onDestroy();
    }

    private static class ServerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Log.w(TAG, "thread name:" + Thread.currentThread().getName());
            switch (msg.what) {
                case MSG_FROM_CLIENT_TO_SERVER:
                    Log.w(TAG, "receive msg from client");
                    Bundle clientBundle = msg.getData();
                    int num1 = clientBundle.getInt(IPCMessengerActivity.KEY_NUM1);
                    int num2 = clientBundle.getInt(IPCMessengerActivity.KEY_NUM2);
                    int result = num1 * num2;

                    //获取Messenger
                    Messenger mClientMessenger = msg.replyTo;
                    // service发送消息给client
                    Message toClientMsg = Message.obtain(null, MSG_FROM_SERVER_TO_CLIENT);
                    Bundle serverBundle = new Bundle();
                    serverBundle.putInt(IPCMessengerActivity.KEY_RESULT, result);
                    toClientMsg.setData(serverBundle);
                    try {
                        Log.w(TAG, "server begin send msg to client");
                        mClientMessenger.send(toClientMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
