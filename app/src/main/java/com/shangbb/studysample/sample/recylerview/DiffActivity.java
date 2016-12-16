package com.shangbb.studysample.sample.recylerview;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.shangbb.studysample.R;
import com.shangbb.studysample.base.BaseActivity;
import com.shangbb.studysample.sample.recylerview.data.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/12/16  16:06
 */
public class DiffActivity extends BaseActivity {

    private List<TestBean> mDatas;
    private RecyclerView mRecyclerView;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
    }

    @Override
    protected void initViews() {
        super.initViews();
        
        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        Button mButton = (Button) findViewById(R.id.btn);
        mButton.setVisibility(View.VISIBLE);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    private void initDatas() {

        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("唐森", "MT", R.drawable.image0));
        mDatas.add(new TestBean("孙大胜", "全职输出", R.drawable.image1));
        mDatas.add(new TestBean("朱巴结", "划水", R.drawable.image2));
        mDatas.add(new TestBean("沙是滴", "辅助", R.drawable.image3));
        mDatas.add(new TestBean("白聋嘛", "MT座驾", R.drawable.image4));
    }

    /**
     * 模拟刷新操作
     */
    public void onRefresh() {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据 ，模拟刷新操作
            }
            mNewDatas.add(new TestBean("赵子龙", "帅", mNewDatas.get(3).getPic()));//模拟新增数据
            mNewDatas.get(0).setDesc("小白脸");
            mNewDatas.get(0).setPic(mNewDatas.get(1).getPic());//模拟修改数据
            TestBean testBean = mNewDatas.get(1);//模拟数据位移
            mNewDatas.remove(testBean);
            mNewDatas.add(testBean);

            //新宠
            //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //放在子线程中计算DiffResult
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
                    Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                    message.obj = diffResult;//obj存放DiffResult
                    message.sendToTarget();
                }
            }).start();
            //mAdapter.notifyDataSetChanged();//以前普通青年的我们只能这样，现在我们是文艺青年了，有新宠了

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static final int H_CODE_UPDATE = 1;
    private List<TestBean> mNewDatas;//增加一个变量暂存newList
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    //取出Result
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
                    diffResult.dispatchUpdatesTo(mAdapter);

                    //这种方法可以fix add 0 不滑动
                    /*diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                        @Override
                        public void onInserted(int position, int count) {
                            mAdapter.notifyItemRangeInserted(position, count);
                            if (position==0){
                                mRv.scrollToPosition(0);
                            }
                        }
                        @Override
                        public void onRemoved(int position, int count) {
                            mAdapter.notifyItemRangeRemoved(position, count);
                        }
                        @Override
                        public void onMoved(int fromPosition, int toPosition) {
                            mAdapter.notifyItemMoved(fromPosition, toPosition);
                        }
                        @Override
                        public void onChanged(int position, int count, Object payload) {
                            mAdapter.notifyItemRangeChanged(position, count, payload);
                        }
                    });*/

                    //别忘了将新数据给Adapter
                    mDatas = mNewDatas;
                    mAdapter.setDatas(mDatas);
                    break;
            }
        }
    };
}
