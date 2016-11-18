package com.shangbb.studysample.utils;

import android.content.Context;
import android.widget.Toast;

import com.shangbb.studysample.SysApplication;


/**
 * Toast相关辅助类
 */
public class T {
    private final static boolean isShow = true;

    private T(){
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    public static void showShortToast(CharSequence text) {
        showShortToast(SysApplication.getInstance().getApplicationContext(),text);
    }

    public static void showLongToast(CharSequence text) {
        showLongToast(SysApplication.getInstance().getApplicationContext(),text);
    }

    public static void showShortToast(Context context,CharSequence text) {
        if(isShow)Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }



    public static void showLongToast(Context context,CharSequence text) {
        if(isShow)Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }


}
