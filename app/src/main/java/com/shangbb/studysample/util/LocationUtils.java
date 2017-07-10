package com.shangbb.studysample.util;

import android.location.Location;

/**
 * @Fuction:
 * @Author: BBShang
 * @Date: 2017/5/23 13:32
 */

public class LocationUtils {

    private static final double EARTH_RADIUS = 6378137;


    /**
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     *
     * @return 单位：M
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2,
                                     double latitude2) {
        double radLat1 = rad(latitude1);
        double radLat2 = rad(latitude2);
        double a = radLat1 - radLat2;
        double b = rad(longitude1) - rad(longitude2);
        double s =
                2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                        * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     *
     * @return 单位：Miles
     */
    public static float getDistanceInMiles(double latitude1, double longitude1, double latitude2,
                                     double longitude2) {
        float [] dist = new float[1];
        Location.distanceBetween(latitude1, longitude1, latitude2, longitude2, dist);
        return dist[0] * 0.000621371192f;
    }

    /**
     * 粗略计算近距离两点距离
     * @param latitude1
     * @param longitude1
     * @param latitude2
     * @param longitude2
     *
     * @return
     */
    public static double getDistanceSimple(double latitude1, double longitude1, double latitude2,
                               double longitude2) {
        double x = (longitude2 - longitude1) * Math.cos((latitude1 + latitude2) / 2);
        double y = (latitude2 - latitude1);
        return  Math.sqrt(x * x + y * y) * EARTH_RADIUS;
    }
}
