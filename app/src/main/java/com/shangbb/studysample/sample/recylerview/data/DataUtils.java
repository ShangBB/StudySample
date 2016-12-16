package com.shangbb.studysample.sample.recylerview.data;

import com.shangbb.studysample.R;

import java.util.ArrayList;


public class DataUtils {

    private static ArrayList<Data> datas = new ArrayList<>();

    static {
        int[] images = new int[]{R.drawable.image0, R.drawable.image1,
                R.drawable.image2,R.drawable.image3, R.drawable.image4};
        for (int i = 0; i < 100; i++) {
            Data data = new Data();
            data.setNum("number is " + i);
            data.setTime("2016-12-12 -- " + i);

            if (i%5 == 0){
                data.setResImage(R.drawable.image0);
            }
            data.setResImage(images[i%5]);
            datas.add(data);
        }
    }


    public static ArrayList<Data> getDatas() {
        return datas;
    }

    public static void setDatas(ArrayList<Data> datas) {
        DataUtils.datas = datas;
    }
}
