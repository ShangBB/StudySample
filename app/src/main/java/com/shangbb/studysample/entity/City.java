package com.shangbb.studysample.entity;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

/**
 * @Fuction:
 * @Author: BBShang
 * @Date: 2017/4/7 17:44
 */

public class City extends DataSupport{

    /**
     * id : JP1848354
     * cityEn : Yokohama
     * cityZh : 横滨市
     * continent : 亚洲
     * countryCode : JP
     * countryEn : Japan
     * lat : 35.447781
     * lon : 139.642502
     */

    @SerializedName("id")
    private String cityId;
    private String cityEn;
    private String cityZh;
    private String continent;
    private String countryCode;
    private String countryEn;
    private float lat;
    private float lon;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCityZh() {
        return cityZh;
    }

    public void setCityZh(String cityZh) {
        this.cityZh = cityZh;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
