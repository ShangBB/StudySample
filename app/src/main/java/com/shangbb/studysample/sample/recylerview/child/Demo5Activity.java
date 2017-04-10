package com.shangbb.studysample.sample.recylerview.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.widget.RecyclerItemClickListener;

public class Demo5Activity extends Activity {
    private Activity activity;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        activity = this;
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this,
//                LinearLayoutManager.VERTICAL, false));
//        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerview.setLayoutManager(gridLayoutManager);
        final Demo5Adapter adapter = new Demo5Adapter(this);
        recyclerview.setAdapter(adapter);

        recyclerview.addOnItemTouchListener(new RecyclerItemClickListener(activity) {
            @Override
            protected void onItemClick(View view, int position) {
                Toast.makeText(activity, "点击了" + position, Toast.LENGTH_SHORT).show();
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
