package com.shangbb.studysample.sample.customview.baseview.bean;

/**
 * @Fuction: 饼状图 数据
 * @Author: Shang
 * @Date: 2016/11/18  16:53
 */
public class PicData {

    // 用户关心数据
    private String name;
    private float value;    //数值
    private float percentage; //百分比

    // 非用户关心数据
    private int color = 0;
    private float angle = 0;

    public PicData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
