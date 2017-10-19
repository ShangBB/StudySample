package com.shangbb.studysample.sample.customview.recordview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.util.T;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.shangbb.studysample.sample.customview.recordview.RecordView.MODEL_PLAY;
import static com.shangbb.studysample.sample.customview.recordview.RecordView.MODEL_RECORD;

/**
 * @Fuction: RecordView 演示
 * @Author: BBShang
 * @Date: 2017/4/11 14:41
 */

public class RecordViewActivity extends BaseActivity implements View.OnTouchListener {

    private RecordView mRecordView;
    private int nowModel = MODEL_RECORD;
    private ScheduledExecutorService mExecutorService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int db = new Random().nextInt(100); //随机模拟音量大小
            mRecordView.setVolume(db);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordview);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("RecordView");
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRecordView = (RecordView) findViewById(R.id.record_view);
        mRecordView.setModel(MODEL_RECORD);
        mRecordView.setCountDownTime(9);
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.btn_speak).setOnTouchListener(this);
        findViewById(R.id.btn_switch_mode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.btn_switch_mode) {
            if (nowModel == MODEL_PLAY) {
                mRecordView.setModel(MODEL_RECORD);
                nowModel = MODEL_RECORD;
            } else {
                mRecordView.setModel(MODEL_PLAY);
                nowModel = MODEL_PLAY;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mRecordView.start();
            mExecutorService = new ScheduledThreadPoolExecutor(1);
            mExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }, 20, 20, TimeUnit.MILLISECONDS);

            mRecordView.setOnCountDownListener(new RecordView.OnCountDownListener() {
                @Override
                public void onCountDown() {
                    T.showShortToast("计时结束啦~~");
                    if (!mExecutorService.isShutdown()){
                        mExecutorService.shutdownNow();
                    }
                }
            });

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (!mExecutorService.isShutdown()){
                mRecordView.stop();
            }

        }
        return false;
    }
}
