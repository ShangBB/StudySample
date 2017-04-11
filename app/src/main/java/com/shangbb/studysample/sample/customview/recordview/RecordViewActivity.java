package com.shangbb.studysample.sample.customview.recordview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.util.T;

import java.util.Timer;
import java.util.TimerTask;

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

    private TimerTask timeTask;
    private Timer timeTimer = new Timer(true);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int db = (int) (Math.random() * 100);
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
        mRecordView.setCountdownTime(9);
        mRecordView.setModel(MODEL_RECORD);
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
            timeTimer.schedule(timeTask = new TimerTask() {
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }, 20, 20);

            mRecordView.setOnCountDownListener(new RecordView.OnCountDownListener() {
                @Override
                public void onCountDown() {
                    T.showShortToast("计时结束啦~~");
                }
            });

        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mRecordView.cancel();
        }
        return false;
    }
}
