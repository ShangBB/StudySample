package com.shangbb.studysample.sample.recylerview.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.shangbb.studysample.R;

import jp.wasabeef.recyclerview.animators.ScaleInAnimator;


/**
 * 自定义动画
 */
public class Demo3Activity2 extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new GridLayoutManager(this, 3));
//        recyclerview.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        //RecyclerView Animators
        //https://github.com/wasabeef/recyclerview-animators
        recyclerView.setItemAnimator(new ScaleInAnimator());

        recyclerView.setAdapter(new Demo3Adapter(this));
    }
}
