package com.shangbb.studysample.sample.customview;

import android.graphics.Color;
import android.os.Bundle;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.sample.customview.entity.PieData;
import com.shangbb.studysample.sample.customview.view.PicView;

import java.util.ArrayList;

public class PicViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picview);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("PicView");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();

        PicView picView = (PicView) findViewById(R.id.picview);
        ArrayList<PieData> picViews = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            picViews.add(new PieData("测试"+i, 10));
        }
        picView.setData(picViews);

    }
}
