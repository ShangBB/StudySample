package com.shangbb.studysample;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.shangbb.studysample.utils.CrashExceptionHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/4/13  13:45
 */
public class SysApplication extends Application {

    private static SysApplication instance;
    private List<Activity> mList = new LinkedList<Activity>();
    public Vibrator mVibrator;


    /**
     * 获取单例
     */
    public static SysApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);



        configCollectCrashInfo();

    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    // remove Activity
    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

    public void exitApp() {
        for (Activity activity : mList) {
            if (activity != null)
                activity.finish();
        }
    }


    /**app在sd卡的主目录*/
    public final static String APP_MAIN_FOLDER_NAME = "Guide";
    /**本地存放闪退日志的目录*/
    public final static String CRASH_FOLDER_NAME = "crash";
    /**app在sd卡存放图片的目录*/
    public final static String PHOTO_FOLDER_NAME = "photo";
    /**
     * 配置奔溃信息的搜集
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, APP_MAIN_FOLDER_NAME, CRASH_FOLDER_NAME);
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }

}
