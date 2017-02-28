package com.shangbb.studysample.http;

import android.content.Context;
import android.widget.Toast;

import com.shangbb.studysample.SysApplication;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * @Fuction: 异常处理
 * @Author: Shang
 * @Date: 2016/5/18  14:46
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private Context mContext;

    public BaseSubscriber() {
        mContext = SysApplication.getInstance().getApplicationContext();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    /**
     * 对异常进行统一处理
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(mContext, "当前网络不可用,请检查网络设置", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(mContext, "当前网络不可用,请检查网络设置", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
