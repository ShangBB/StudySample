package com.shangbb.studysample.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.customview.CustomViewActivity;
import com.shangbb.studysample.sample.ipc.messenger.IPCMessengerActivity;
import com.shangbb.studysample.sample.recylerview.RecylerViewActivity;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.base.BaseAdapterHelper;
import com.shangbb.studysample.base.ViewHolder;
import com.shangbb.studysample.sample.customview.entity.ActivityBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

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
        toolbar.setTitle("Study List");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
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
        mList.add(new ActivityBean("CustomView", CustomViewActivity.class));
        mList.add(new ActivityBean("RecyclerView", RecylerViewActivity.class));
        mList.add(new ActivityBean("IPCMessenger", IPCMessengerActivity.class));
        mAdapter.notifyDataSetChanged();
    }

}
