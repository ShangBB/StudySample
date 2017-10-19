package com.shangbb.studysample.sample.customview.baseview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shangbb.studysample.sample.customview.baseview.view.FlipboardView;

/**
 * @Fuction: 翻页效果
 * @Author: BBShang
 * @Date: 2017/10/19 11:38
 */

public class FlipboardFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout layout = new LinearLayout(getActivity());
        FlipboardView flipboardView = new FlipboardView(layout.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        flipboardView.setLayoutParams(layoutParams);
        //添加到线性布局
        layout.addView(flipboardView);
        return layout;
    }
}
