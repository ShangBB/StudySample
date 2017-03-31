package com.shangbb.studysample.sample.recylerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shangbb.studysample.R;
import com.shangbb.studysample.sample.recylerview.data.TestBean;

import java.util.List;

/**
 * @Fuction:
 * @Author: Shang
 * @Date: 2016/12/16  16:14
 */
public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffVH> {

    private Context mContext;
    private List<TestBean> mDatas;
    private LayoutInflater mInflater;

    public DiffAdapter(Context context, List<TestBean> datas) {
        mContext = context;
        mDatas = datas;

        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<TestBean> datas) {
        mDatas = datas;
    }

    @Override
    public DiffVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiffVH(mInflater.inflate(R.layout.item_diff, parent, false));
    }

    @Override
    public void onBindViewHolder(DiffVH holder, int position) {
        TestBean bean = mDatas.get(position);
        holder.tv1.setText(bean.getName());
        holder.tv2.setText(bean.getDesc());
        holder.iv.setImageResource(bean.getPic());
    }

    /**
     *
     * @param holder
     * @param position
     * @param payloads
     *      如果payloads list 不为空，那么当前绑定了旧数据的ViewHolder 和Adapter， 可以使用 payload的数据进行一次 高效的部分更新。
     *      如果payloads 是空的，Adapter必须进行一次完整绑定（调用两参方法）。
     *      payloads对象不会为null，但是它可能是空（empty），这时候需要完整绑定(所以我们在方法里只要判断isEmpty就好，不用重复判空)。
     */
    @Override
    public void onBindViewHolder(DiffVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position);
        }else {
            //文艺青年中的文青
            Bundle payload = (Bundle) payloads.get(0);//取出我们在getChangePayload（）方法返回的bundle
            TestBean bean = mDatas.get(position);//取出新数据源，（可以不用）
            for (String key : payload.keySet()) {
                switch (key) {
                    case "KEY_DESC":
                        //这里可以用payload里的数据，不过data也是新的 也可以用
                        holder.tv2.setText(bean.getDesc());
                        break;
                    case "KEY_PIC":
                        holder.iv.setImageResource(payload.getInt(key));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public class DiffVH extends RecyclerView.ViewHolder{
        TextView tv1, tv2;
        ImageView iv;

        public DiffVH(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
