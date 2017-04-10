package com.shangbb.studysample.sample.recylerview.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.data.DataUtils;

/**
 * RecyclerView 简单用法
 */
public class Demo1Activity extends Activity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerview.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
        //瀑布流
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));

        recyclerview.setAdapter(new Demo1Adapter(this, DataUtils.getDatas()));
    }
}
