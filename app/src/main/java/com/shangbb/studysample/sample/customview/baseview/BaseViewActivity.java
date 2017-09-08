package com.shangbb.studysample.sample.customview.baseview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.shangbb.studysample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction: 自定义View基础练习
 * @Author: BBShang
 * @Date: 2017/7/18 16:18
 */

public class BaseViewActivity extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseview);

        //Fragment+ViewPager+FragmentViewPager组合的使用
        ViewPager viewPager = (ViewPager) findViewById(R.id.bezier_viewpager);
        fragmentList.add(new DrawXFragment());
        fragmentList.add(new BezierTwoFragment());
        fragmentList.add(new PicViewFragment());
        titleList.add("drawXXX");
        titleList.add("BezierTwo");
        titleList.add("PicView");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.bezier_tab);
        tabLayout.setupWithViewPager(viewPager);
        //TabGravity:放置Tab的Gravity,有GRAVITY_CENTER 和 GRAVITY_FILL两种效果。
        //一个是居中，另一个是尽可能的填充（注意，GRAVITY_FILL需要和MODE_FIXED一起使用才有效果）
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //TabMode:布局中Tab的行为模式（behavior mode），有两种值：MODE_FIXED 和 MODE_SCROLLABLE。
        //MODE_FIXED:固定tabs，并同时显示所有的tabs。
        //MODE_SCROLLABLE：可滚动tabs，显示一部分tabs，在这个模式下能包含长标签和大量的tabs，最好用于用户不需要直接比较tabs。
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
