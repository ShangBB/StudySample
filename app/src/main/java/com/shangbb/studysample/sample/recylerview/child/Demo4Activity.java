package com.shangbb.studysample.sample.recylerview.child;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.widget.DividerItemDecoration;

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

        // 默认系统的divider
        DividerItemDecoration dividerDefault = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        // 自定义图片drawable分的divider
        DividerItemDecoration dividerDrawable = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, getResources().getDrawable(R.mipmap.ic_launcher));
        // 自定义无高宽的drawable的divider - 垂直列表
        DividerItemDecoration dividerV = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, new ColorDrawable(Color.parseColor("#ff00ff")));
        dividerV.setHeight(1);
        // 自定义无高宽的drawable的divider - 水平列表
        DividerItemDecoration dividerH = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST, new ColorDrawable(Color.parseColor("#ff00ff")));
        dividerH.setWidth(1);

        recyclerview.addItemDecoration(dividerH);
        recyclerview.addItemDecoration(dividerV);

        recyclerview.setAdapter(new Demo3Adapter(this));
    }
}
