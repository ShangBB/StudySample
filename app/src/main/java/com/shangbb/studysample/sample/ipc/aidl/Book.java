package com.shangbb.studysample.sample.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Fuction:
 * @Author: BBShang
 * @Date: 2017/3/30 17:32
 */

public class Book implements Parcelable {

    private String name;
    private int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static Creator<Book> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.price);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
        price = dest.readInt();
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.price = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    //方便打印数据
    @Override
    public String toString() {
        return "name : " + name + " , price : " + price;
    }
}
