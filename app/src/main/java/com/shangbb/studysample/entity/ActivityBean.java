package com.shangbb.studysample.entity;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/11/17  14:43
 */
public class ActivityBean {

    private String title;
    private Class<?> infoClass;

    public ActivityBean(String title, Class<?> infoClass) {
        this.title = title;
        this.infoClass = infoClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(Class<?> infoClass) {
        this.infoClass = infoClass;
    }
}
