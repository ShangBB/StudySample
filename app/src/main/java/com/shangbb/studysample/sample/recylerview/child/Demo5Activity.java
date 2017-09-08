package com.shangbb.studysample.sample.recylerview.child;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.widget.OnRecyclerItemClickListener;

public class Demo5Activity extends Activity {
    private Context mContext;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        mContext = this;
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
//        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(gridLayoutManager);
        final Demo5Adapter adapter = new Demo5Adapter(this);
        recyclerview.setAdapter(adapter);

        recyclerview.addOnItemTouchListener(new OnRecyclerItemClickListener(mContext) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                Toast.makeText(mContext, "click " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder, int position) {
                Toast.makeText(mContext, "longClick " + position, Toast.LENGTH_SHORT).show();
            }
        });

        //setSpanSizeLookup可以让你根据position来设置 span size，span size表示一个item的跨度，跨度了多少个span
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isFeatureItem(position) == 1 ? gridLayoutManager.getSpanCount() : 1;
            }
        });
    }
}
