package com.shangbb.studysample.sample.customview.baseview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.sample.customview.baseview.fragment.BezierTwoFragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.DrawXFragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.FlipboardFragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.PaintApi1Fragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.PaintApi2Fragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.PaintApi3Fragment;
import com.shangbb.studysample.sample.customview.baseview.fragment.PicViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction: 自定义View基础练习
 * @Author: BBShang
 * @Date: 2017/7/18 16:18
 */

public class BaseViewActivity extends BaseActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseview);

        //Fragment+ViewPager+FragmentViewPager组合的使用
        viewPager = (ViewPager) findViewById(R.id.bezier_viewpager);
        addTab(new DrawXFragment(), "drawXXX");
        addTab(new PaintApi1Fragment(), "paintApi1");
        addTab(new PaintApi2Fragment(), "paintApi2");
        addTab(new PaintApi3Fragment(), "paintApi3");
        addTab(new BezierTwoFragment(), "BezierTwo");
        addTab(new PicViewFragment(), "PicView");
        addTab(new FlipboardFragment(), "翻页");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.bezier_tab);
        tabLayout.setupWithViewPager(viewPager);
        //TabGravity:放置Tab的Gravity,有GRAVITY_CENTER 和 GRAVITY_FILL两种效果。
        //一个是居中，另一个是尽可能的填充（注意，GRAVITY_FILL需要和MODE_FIXED一起使用才有效果）
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //TabMode:布局中Tab的行为模式（behavior mode），有两种值：MODE_FIXED 和 MODE_SCROLLABLE。
        //MODE_FIXED:固定tabs，并同时显示所有的tabs。
        //MODE_SCROLLABLE：可滚动tabs，显示一部分tabs，在这个模式下能包含长标签和大量的tabs，最好用于用户不需要直接比较tabs。
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
                viewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("BaseView");
        setSupportActionBar(toolbar);
        initBack();
    }

    private void addTab(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
