package com.shangbb.studysample.sample.recylerview;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.widget.Divider;

/**
 * 自定义divider
 */
public class Demo4Activity extends Activity {

    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerview.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        Divider dividerV = new Divider(new ColorDrawable(0xffff0000), OrientationHelper.VERTICAL);
        Divider dividerH = new Divider(new ColorDrawable(0xffff0000), OrientationHelper.HORIZONTAL);
        //单位:px
        dividerV.setHeight(5);
        dividerH.setWidth(5);
        recyclerview.addItemDecoration(dividerV);
        recyclerview.addItemDecoration(dividerH);

        recyclerview.setAdapter(new Demo3Adapter(this));
    }
}
