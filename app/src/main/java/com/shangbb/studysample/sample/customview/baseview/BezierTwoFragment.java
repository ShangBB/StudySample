package com.shangbb.studysample.sample.customview.baseview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shangbb.studysample.sample.customview.baseview.view.BezierTwo;

/**
 * @Fuction: 展示二阶贝塞尔曲线
 * @Author: BBShang
 * @Date: 2017/7/18 16:21
 */

public class BezierTwoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout  = new LinearLayout(getContext());
        //创建
        BezierTwo bezierTwo = new BezierTwo(layout.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,0,0,0);
        bezierTwo.setLayoutParams(layoutParams);
        //添加到线性布局
        layout.addView(bezierTwo);
        return layout;
    }
}
