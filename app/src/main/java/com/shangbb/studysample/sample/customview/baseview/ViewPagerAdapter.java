package com.shangbb.studysample.sample.customview.baseview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction: ViewPagerAdapter
 * @Author: BBShang
 * @Date: 2017/5/17 15:58
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment = new ArrayList<>();                         //fragment列表
    private List<String> listTitle = new ArrayList<>();                              //tab名的列表


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> listFragment, List<String> listTitle) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return listTitle.get(position);
    }
}
