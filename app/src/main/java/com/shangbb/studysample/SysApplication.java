package com.shangbb.studysample;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.shangbb.studysample.util.CrashExceptionHandler;

import org.litepal.LitePalApplication;

import java.util.List;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/4/13  13:45
 */
public class SysApplication extends LitePalApplication{

    private static SysApplication instance;

    public static SysApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //configCollectCrashInfo();
    }


    /**app在sd卡的主目录*/
    public final static String APP_MAIN_FOLDER_NAME = "ASample";
    /**本地存放闪退日志的目录*/
    public final static String CRASH_FOLDER_NAME = "crash";
    /**
     * 配置奔溃信息的搜集
     */
    private void configCollectCrashInfo() {
        CrashExceptionHandler crashExceptionHandler = new CrashExceptionHandler(this, APP_MAIN_FOLDER_NAME, CRASH_FOLDER_NAME);
        Thread.setDefaultUncaughtExceptionHandler(crashExceptionHandler);
    }


    /**======================================多进程初始化方案 START==============================================*/
    private void init() {
        String processName = getProcessName(this, android.os.Process.myPid());
        if (!TextUtils.isEmpty(processName) && processName.equals(this.getPackageName())) {//判断进程名，保证只有主进程运行

        }
    }


    /**
     * 根据Pid得到进程名
     */
    private String getProcessName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return "";
    }

    /**======================================多进程初始化方案 END===============================================*/

}
