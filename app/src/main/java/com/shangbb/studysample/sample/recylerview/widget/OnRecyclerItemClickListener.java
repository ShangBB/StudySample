package com.shangbb.studysample.sample.recylerview.widget;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public abstract class OnRecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView mRecyclerView;
    private GestureDetectorCompat mGestureDetectorCompat;//手势探测器

    public OnRecyclerItemClickListener(Context context) {
        mGestureDetectorCompat = new GestureDetectorCompat(context, new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        mRecyclerView = view;
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        mRecyclerView = view;
        mGestureDetectorCompat.onTouchEvent(motionEvent);
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        //一次单独的轻触抬起手指操作，就是普通的点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder, mRecyclerView.getChildAdapterPosition(childViewUnder));
            }
            return true;
        }

        //长按屏幕超过一定时长，就会触发，就是长按事件
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder, mRecyclerView.getChildAdapterPosition(childViewUnder));
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder, int position);

    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder, int position);
}

