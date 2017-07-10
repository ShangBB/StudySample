package com.shangbb.studysample.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.base.BaseAdapterHelper;
import com.shangbb.studysample.base.ViewHolder;
import com.shangbb.studysample.entity.ActivityBean;
import com.shangbb.studysample.entity.City;
import com.shangbb.studysample.sample.customview.CustomViewActivity;
import com.shangbb.studysample.sample.ipc.IpcActivity;
import com.shangbb.studysample.sample.recylerview.RecylerViewActivity;
import com.shangbb.studysample.util.LogUtils;
import com.shangbb.studysample.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
        //saveDb();
    }

    @Override
    protected void initToolBar() {
        super.initToolBar();
        toolbar.setTitle("Study List");
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
        mList.add(new ActivityBean("自定义View", CustomViewActivity.class));
        mList.add(new ActivityBean("RecyclerView", RecylerViewActivity.class));
        mList.add(new ActivityBean("IPC 机制", IpcActivity.class));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 将city.json中的数据存入数据库
     */
    private void saveDb() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                InputStream is = null;
                try {
                    is = getAssets().open("city.json");
                    if (is != null) {
                        JsonReader reader = null;
                        reader = new JsonReader(new InputStreamReader(is));
                        Type listType = new TypeToken<ArrayList<City>>() {
                        }.getType();
                        ArrayList<City> cityArrayList = new Gson().fromJson(reader, listType);

                        for (int i = 0; i < cityArrayList.size(); i++) {
                            City city = cityArrayList.get(i);
                            city.setCityEn(StringUtils.upperFirstLetter(city.getCityEn()));
                            city.saveThrows();
                            LogUtils.e("\n-->>" + i + "-->>" +city.getCityEn());
                        }
                        LogUtils.e("存储结束===========================================================");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();

    }



}
