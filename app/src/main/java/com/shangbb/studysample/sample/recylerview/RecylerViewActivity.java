package com.shangbb.studysample.sample.recylerview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.base.BaseAdapterHelper;
import com.shangbb.studysample.base.ViewHolder;
import com.shangbb.studysample.entity.ActivityBean;
import com.shangbb.studysample.sample.recylerview.child.Demo1Activity;
import com.shangbb.studysample.sample.recylerview.child.Demo2Activity;
import com.shangbb.studysample.sample.recylerview.child.Demo3Activity1;
import com.shangbb.studysample.sample.recylerview.child.Demo3Activity2;
import com.shangbb.studysample.sample.recylerview.child.Demo4Activity;
import com.shangbb.studysample.sample.recylerview.child.Demo5Activity;
import com.shangbb.studysample.sample.recylerview.child.DiffActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/12/15  9:51
 */
public class RecylerViewActivity extends BaseActivity{

    private ListView mListView;
    private BaseAdapterHelper<ActivityBean> mAdapter;
    private List<ActivityBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_listview);
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("RecylerView");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        initBack();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new BaseAdapterHelper<ActivityBean>(mContext, mList, R.layout.item_textview) {
            @Override
            public void convert(ViewHolder holder, ActivityBean mainItem) {
                holder.setText(R.id.tv_item_title, mainItem.getTitle());
            }
        };
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityBean mainItem = mAdapter.getItem(position);
                openActivity(mContext, mainItem.getInfoClass());
            }
        });
        setData();
    }

    private void setData() {
        mList.add(new ActivityBean("简单用法", Demo1Activity.class));
        mList.add(new ActivityBean("多type holder", Demo2Activity.class));
        mList.add(new ActivityBean("默认动画", Demo3Activity1.class));
        mList.add(new ActivityBean("自定义动画", Demo3Activity2.class));
        mList.add(new ActivityBean("自定义divider", Demo4Activity.class));
        mList.add(new ActivityBean("宽度全屏的item  点击事件", Demo5Activity.class));
        mList.add(new ActivityBean("DiffUtil 计算差异刷新", DiffActivity.class));
        mAdapter.notifyDataSetChanged();
    }
}
