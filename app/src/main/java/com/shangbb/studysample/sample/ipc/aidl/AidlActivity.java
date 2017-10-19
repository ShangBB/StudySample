package com.shangbb.studysample.sample.ipc.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shangbb.studysample.IBookManager;
import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.util.T;

import java.util.List;

/**
 * @Fuction: 客户端的AIDLActivity.java
 * @Author: BBShang
 * @Date: 2017/3/24 19:07
 */

public class AidlActivity extends BaseActivity {

    //由AIDL文件生成的Java类
    private IBookManager mBookManager = null;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    //包含Book对象的list
    private List<Book> mBooks;

    private EditText mNameEt, mPriceEt;
    private TextView mLogTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_aidl);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mNameEt = (EditText) findViewById(R.id.edit_name);
        mPriceEt = (EditText) findViewById(R.id.edit_price);
        mLogTv = (TextView) findViewById(R.id.tv_log);
    }

    @Override
    protected void initListener() {
        super.initListener();

        findViewById(R.id.btn_bind).setOnClickListener(this);
        findViewById(R.id.btn_unbind).setOnClickListener(this);
        findViewById(R.id.btn_add_in).setOnClickListener(this);
        findViewById(R.id.btn_add_out).setOnClickListener(this);
        findViewById(R.id.btn_add_inout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.btn_bind:
                attemptToBindService();
                break;

            case R.id.btn_unbind:
                excuteUnbindService();
                break;

            case R.id.btn_add_in:
                addBook(1);
                break;

            case R.id.btn_add_out:
                addBook(2);
                break;

            case R.id.btn_add_inout:
                addBook(3);
                break;

            default:
                break;
        }
    }


    /**
     * 按钮的点击事件，点击之后调用服务端的addBookIn方法
     */
    public void addBook(int inout) {
        //如果与服务端的连接处于未连接状态，则尝试连接
        if (!mBound) {
            Toast.makeText(this, "请先绑定服务", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mBookManager == null) {
            return;
        }

        String name = mNameEt.getText().toString();
        String price = mPriceEt.getText().toString();

        if (name.isEmpty() || price.isEmpty()) {
            T.showShortToast("请先输入书名和价格");
            return;
        }

        Book book = new Book();
        book.setName(name);
        book.setPrice(Integer.parseInt(price));
        try {
            switch (inout) {
                case 1:
                    mBookManager.addBookIn(book);
                    break;

                case 2:
                    mBookManager.addBookOut(book);
                    break;

                case 3:
                    mBookManager.addBookInout(book);
                    break;

                default:
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            mBooks = mBookManager.getBooks();
            mLogTv.setText("客户端对象:" + book.toString() + "\n" + "返回值:" + mBooks.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.bbshang.aidl");
        intent.setPackage("com.shangbb.studysample");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private void excuteUnbindService() {
        if (mBound) {
            unbindService(mServiceConnection);
            T.showShortToast("unbind");
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        excuteUnbindService();
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            mBookManager = IBookManager.Stub.asInterface(service);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    mLogTv.setText(mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

}
