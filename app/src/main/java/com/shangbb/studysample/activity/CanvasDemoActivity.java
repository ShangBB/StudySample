package com.shangbb.studysample.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.entity.PieData;
import com.shangbb.studysample.view.PicView;

import java.util.ArrayList;

public class CanvasDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas_demo);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("CanvasDemo");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();

        PicView picView = (PicView) findViewById(R.id.picview);
        ArrayList<PieData> picViews = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            picViews.add(new PieData("测试"+i, i));
        }
        picView.setData(picViews);

    }
}
