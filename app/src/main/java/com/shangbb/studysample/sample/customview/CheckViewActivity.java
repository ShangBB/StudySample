package com.shangbb.studysample.sample.customview;

import android.graphics.Color;
import android.os.Bundle;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.sample.customview.view.CheckView;

public class CheckViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkview);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("CheckView");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();

        CheckView checkView = (CheckView) findViewById(R.id.checkview);
        checkView.setAnimDuration(1500);
        checkView.check();

    }

}
