package com.shangbb.studysample.sample.customview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.sample.customview.view.NumKeyboard;

import java.lang.reflect.Method;

public class KeyboardActicity extends BaseActivity{

    private Context mCtx;
    private Activity mActivity;
    private EditText mEditClass;
    private EditText mEditScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("KeyboardView");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();

        //get the codes for xml
        char c;
        int i;
        String[] privinces = { "一",  "二", "三", "四", "五", "六", "年", "级", "班", "."};
        for(String str : privinces){
            c=str.toCharArray()[0];
            i=c;
            //xml中格式：<Key android:codes="19968" android:keyLabel="一" />
            Log.i("key","<Key android:codes=\""+i+"\" android:keyLabel=\""+c+"\" />");
        }


        mEditClass = (EditText) this.findViewById(R.id.edit_class);
        mEditScore = (EditText) this.findViewById(R.id.edit_score);


        //禁止弹出系统默认的软键盘
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            Class<EditText> cls = EditText.class;
            Method setSoftInputShownOnFocus;
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(mEditClass, false);
            setSoftInputShownOnFocus.invoke(mEditScore, false);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //禁止长按选择
        mEditClass.setCustomSelectionActionModeCallback(new Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //这里可以添加自己的菜单选项（前提是要返回true的）
                return false;//返回false 就是屏蔽ActionMode菜单
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        mEditScore.setCustomSelectionActionModeCallback(new Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                //这里可以添加自己的菜单选项（前提是要返回true的）
                return false;//返回false 就是屏蔽ActionMode菜单
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });


        //设置监听动作，弹出自定义键盘
        mCtx = this;
        mActivity = this;
        mEditClass.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new NumKeyboard(mActivity, mCtx, mEditClass).showChinese();
                return false;
            }
        });

        mEditScore.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new NumKeyboard(mActivity, mCtx, mEditScore).showNumber();
                return false;
            }
        });
    }
}
