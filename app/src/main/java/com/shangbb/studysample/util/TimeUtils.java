package com.shangbb.studysample.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * TimeUtils
 */
public class TimeUtils {

    private TimeUtils() {
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    /**
     * 获取当前时间
     * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String dateString;
        dateString = df.format(Calendar.getInstance().getTime());
        return dateString;
    }

    /**
     * 获取当前时间
     * @return 返回字符串格式 MM月dd日 HH:mm
     */
    public static String getStringDate2() {
        DateFormat df = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
        String dateString;
        dateString = df.format(Calendar.getInstance().getTime());
        return dateString;
    }

    /**
     * 获取时间戳字符串
     * @return
     */
    public static String getTimestamp() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        Log.d("timestamp==>>",timestamp);
        return timestamp;
    }

    /**
     * 根据long类型的时间戳，转换为一个String类型的描述性时间
     * 通话记录如果发生在今天：“15：30”
     * 发生在昨天：“昨天8:23”
     * 发生在前天：“前天4:56”
     * 更早：     “2016/04/15”
     * @param timeStample 聊天记录发生的时间
     * @return
     */
    public static String getTime(long timeStample) {
        //得到现在的时间戳
        long now = System.currentTimeMillis();
        //在java中,int类型的数进行除法运算,只能的整数,正是利用这一点,
        //在下列日期中,只要没过昨天24点,无论相差了1s还是23小时,除法得到的结果都是前一天,
        int day = (int) (now / 1000 / 60 / 60 / 60 - timeStample / 1000 / 60 / 60 / 60);
        switch (day) {
            //如果是0这则说明是今天,显示时间
            case 0:
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
                return sdf.format(timeStample);
            //如果是1说明是昨天,显示昨天+时间
            case 1:
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm", Locale.CHINA);
                return "昨天 " + sdf1.format(timeStample);
            //如果是1说明是前天,显示前天+时间
            case 2:
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.CHINA);
                return "前天 " + sdf2.format(timeStample);
            //结果大于2
            default:
                SimpleDateFormat sdf3 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
                return sdf3.format(timeStample);
        }
    }
}
