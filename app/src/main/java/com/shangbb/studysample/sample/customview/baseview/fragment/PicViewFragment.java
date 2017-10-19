package com.shangbb.studysample.sample.customview.baseview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.shangbb.studysample.sample.customview.baseview.bean.PicData;
import com.shangbb.studysample.sample.customview.baseview.view.PicView;

import java.util.ArrayList;


public class PicViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout layout = new LinearLayout(getActivity());
        //创建
        PicView picView = new PicView(layout.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 0);
        picView.setLayoutParams(layoutParams);
        //添加到线性布局
        layout.addView(picView);

        ArrayList<PicData> picViews = new ArrayList<>();
        for (int i = 0; i < 7; i++){
            picViews.add(new PicData("测试"+i, 10));
        }
        picView.setData(picViews);
        return layout;
    }
}
