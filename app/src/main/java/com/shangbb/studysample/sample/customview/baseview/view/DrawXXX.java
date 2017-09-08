package com.shangbb.studysample.sample.customview.baseview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Fuction: 练习drawXXX
 * @Author: BBShang
 * @Date: 2017/7/20 16:19
 */

public class DrawXXX extends View {
    private Paint mPaint;
    private RectF mRectF;

    public DrawXXX(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectF = new RectF();
    }

    public DrawXXX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //使用 canvas.drawCircle() 方法画圆
        //一共四个圆：1.实心圆 2.空心圆 3.蓝色实心圆 4.线宽为 20 的空心圆
        mPaint.reset();
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(350, 200, 100, mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(700, 200, 100, mPaint);

        mPaint.reset();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(350, 500, 100, mPaint);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(700, 500, 100, mPaint);

        //使用 canvas.drawRect() 方法画矩形
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawRect(100,50,950,650, mPaint);

        //使用 canvas.drawPoint() 方法画点
        //一个圆点，一个方点
        //圆点和方点的切换使用 paint.setStrokeCap(cap)：`ROUND` 是圆点，`BUTT` 或 `SQUARE` 是方点
        mPaint.reset();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(700,200, mPaint);

        mPaint.reset();
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(700,500, mPaint);

        //使用 canvas.drawOval() 方法画椭圆
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mRectF.set(100,50,950,650);
        canvas.drawOval(mRectF,mPaint);

        //使用 canvas.drawRoundRect() 方法画圆角矩形
        mPaint.reset();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mRectF.set(80,30,970,670);
        canvas.drawRoundRect(mRectF,10,10,mPaint);

        //使用 canvas.drawArc() 方法画弧形和扇形
        mPaint.reset();
        mPaint.setColor(Color.RED);
        mRectF.set(100,750,600,950);
        canvas.drawArc(mRectF, 0, 60, true, mPaint);
        canvas.drawArc(mRectF, 90, 90, false, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRectF, 180, 180, false, mPaint);

    }
}
