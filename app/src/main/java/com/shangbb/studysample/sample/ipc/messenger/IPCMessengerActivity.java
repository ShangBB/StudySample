package com.shangbb.studysample.sample.ipc.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.util.T;

import java.lang.ref.WeakReference;

/**
 * @Fuction:
 * @Author: BBShang
 * @Date: 2017/3/24 11:12
 */

public class IPCMessengerActivity extends BaseActivity {

    public static final String TAG = "IPCMessengerActivity";

    private EditText mEditNum1;
    private EditText mEditNum2;
    private TextView mResultTv;

    public final static String KEY_NUM1 = "num1";
    public final static String KEY_NUM2 = "num2";
    public final static String KEY_RESULT = "result";

    private ServiceConnection sc = new KServiceConnection();
    private boolean mBound = false;

    private Messenger mServerMessenger;

    private Handler mClientHandler = new MyClientHandler(IPCMessengerActivity.this);
    private Messenger mClientMessenger = new Messenger(mClientHandler);

    private static class MyClientHandler extends Handler {

        private final WeakReference<Activity> mActivityReference;

        MyClientHandler(Activity activity) {
            this.mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            IPCMessengerActivity activity = (IPCMessengerActivity) mActivityReference.get();  //获取弱引用队列中的activity
            if (msg.what == KMessengerService.MSG_FROM_SERVER_TO_CLIENT) {
                Log.w(TAG, "reveive msg from server");
                Bundle serverBundle = msg.getData();
                int result = serverBundle.getInt(KEY_RESULT);
                activity.mResultTv.setText(String.valueOf(result));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_messenger);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mEditNum1 = (EditText) findViewById(R.id.edit_num1);
        mEditNum2 = (EditText) findViewById(R.id.edit_num2);
        mResultTv = (TextView) findViewById(R.id.tv_result);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("IPCMessager");
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.btn_bind).setOnClickListener(this);
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.btn_bind:
                /*
                 * 绑定服务后,会调用ServiceConnection的onServiceConnected方法,通过Messager发送消息,服务器端的Handler就能够收到消息了
                 * 这样的话我们就能够通过bindService获取到一个包含Binder的Messager进行通信了,实现了客户端对服务器端传递消息
                 */
                Intent intent = new Intent(this, KMessengerService.class);
                bindService(intent, sc, Context.BIND_AUTO_CREATE);

                /*
                 * 如果是跨应用的进程间通信,那么就需要用到隐式意图了.这里有一点需要注意的就是,
                 * 在5.0以后隐式意图开启或者绑定service要setPackage(Service的包名),不然会报错
                 */
                break;

            case R.id.btn_send:
                sendMsg();
                break;

            case R.id.btn_unbind:
                excuteUnbindService();
                break;

            default:
                break;
        }

    }

    class KServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.w(TAG, "KServiceConnection onServiceConnected");
            mServerMessenger = new Messenger(service);
            T.showShortToast("bind");
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            //当Service服务被异外销毁时，例如内存的资源不足时这个方法才被自动调用
            Log.w(TAG, "KServiceConnection onServiceDisconnected");
            mBound = false;
        }
    }

    public void sendMsg() {
        if (!mBound){
            T.showShortToast("请先绑定服务");
            return;
        }

        if (TextUtils.isEmpty(mEditNum1.getText()) || TextUtils.isEmpty(mEditNum2.getText())){
            T.showShortToast("请输入数字");
            return;
        }


        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, KMessengerService.MSG_FROM_CLIENT_TO_SERVER, 0, 0);

        int num1 = Integer.parseInt(mEditNum1.getText().toString());
        int num2 = Integer.parseInt(mEditNum2.getText().toString());
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_NUM1,num1);
        bundle.putInt(KEY_NUM2,num2);
        msg.setData(bundle);
        // 通过replyTo把client端的Messenger(信使)传递给service
        msg.replyTo = mClientMessenger;
        try {
            mServerMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void excuteUnbindService() {
        if (mBound) {
            unbindService(sc);
            T.showShortToast("unbind");
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy");
    }
}
