package com.shangbb.studysample.sample.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.shangbb.studysample.sample.customview.entity.PieData;

import java.util.ArrayList;


/**
 * @Fuction: 简单饼状图
 * @Author: Shang
 * @Date: 2016/11/18  16:49
 */
public class PicView extends View {



    //颜色表
    private int[] mColors = {0xFFFF0000, 0xFFFF7F00, 0xFFFFFF00, 0xFF00FF00, 0xFF007FFF,
            0xFF0000FF, 0xFF7F00FF};

    private float mStartAngle = 0; //初始绘制角度
    private ArrayList<PieData> mData;
    private int mWidth, mHeight;
    private Paint mPaint = new Paint();
    private RectF rectF;

    public PicView(Context context) {
        super(context);
    }

    public PicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public PicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8); //计算半径
        //饼状图绘制区域
        rectF = new RectF(-r, -r, r, r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null == mData) {
            return;
        }

        float currentStartAngle = mStartAngle; //当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2);  //移动画布坐标原点


        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rectF, currentStartAngle, pie.getAngle(), true, mPaint);
            currentStartAngle += pie.getAngle();
        }
    }


    /**
     * 设置起始角度
     * @param startAngle
     */
    public void setStartAngle(float startAngle) {
        mStartAngle = startAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> data) {
        mData = data;
        initData();
        invalidate();
    }

    private void initData() {
        if (null == mData || mData.size() == 0) {
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.getValue(); //计算数值和

            int j = i % mColors.length; //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.getValue() / sumValue; //百分比
            float angle = percentage * 360; //对应角度

            pie.setPercentage(percentage);
            pie.setAngle(angle);
            sumAngle += angle;
        }
    }
}
