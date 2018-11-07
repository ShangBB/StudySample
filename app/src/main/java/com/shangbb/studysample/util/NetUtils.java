package com.shangbb.studysample.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Fuction: 跟网络相关的工具类
 * @Author: Shang
 * @Date: 2016/4/11  14:29
 */
public class NetUtils {
    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     * @param context
     *
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {

                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断WIFI网络是否可用
     * @param context
     *
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            // 获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为WIFI
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     * @param context
     *
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象(包括对wi-fi,net等连接的管理)
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            //判断NetworkInfo对象是否为空 并且类型是否为MOBILE
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息 原生
     * @param context
     *
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            //获取手机所有连接管理对象
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            //获取NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                //返回NetworkInfo的类型
                return networkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2 自定义
     * @param context
     *
     * @return
     */
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService
                    (Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

    /**
     * 判断GPS是否打开 ACCESS_FINE_LOCATION权限
     * @param context
     *
     * @return
     */
    public static boolean isGPSEnabled(Context context) {
        //获取手机所有连接LOCATION_SERVICE对象
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context
                .LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 基础的 HttpURLConnection post 上传
     * @param url
     * @param form
     *
     * @return
     */
    public static String post(String url, Map<String, String> form){
        HttpURLConnection conn = null;
        PrintWriter pw = null ;
        BufferedReader rd = null ;
        StringBuilder out = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        String line;
        String response = null;
        try {
            for (String key : form.keySet()) {
                if(out.length()!=0){
                    out.append("&");
                }
                //URLEncoder.encode(form.get(key), "UTF-8") 记得编码
                out.append(key).append("=").append(URLEncoder.encode(form.get(key), "UTF-8"));
            }
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setUseCaches(false);
            conn.connect();
            pw = new PrintWriter(conn.getOutputStream());
            pw.print(out.toString());
            pw.flush();
            rd  = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = rd.readLine()) != null ) {
                sb.append(line);
            }
            response = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(pw != null){
                    pw.close();
                }
                if(rd != null){
                    rd.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
