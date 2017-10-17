package com.shangbb.studysample.sample.customview.baseview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

/**
 * @Fuction: Paint : setPathEffect
 * @Author: BBShang
 * @Date: 2017/9/19 18:09
 */

public class PaintApi3 extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private PathEffect cornerPathEffect = new CornerPathEffect(20);
    private PathEffect discretePathEffect = new DiscretePathEffect(20, 5);
    private PathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10, 5, 10}, 0);
    private PathEffect pathDashPathEffect;
    private PathEffect sumPathEffect = new SumPathEffect(dashPathEffect, discretePathEffect);
    private PathEffect composePathEffect = new ComposePathEffect(dashPathEffect, discretePathEffect);

    public PaintApi3(Context context) {
        super(context);

        mPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(30);

        path.moveTo(50, 100);
        path.rLineTo(50, 100);
        path.rLineTo(80, -100);
        path.rLineTo(100, 100);
        path.rLineTo(70, -120);
        path.rLineTo(150, 80);

        Path dashPath = new Path();
        dashPath.lineTo(20, -30);
        dashPath.lineTo(40, 0);
        dashPath.close();
        pathDashPathEffect = new PathDashPathEffect(dashPath, 50, 0, PathDashPathEffect.Style.MORPH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setPathEffect() 来设置不同的 PathEffect
        canvas.drawText("setPathEffect 给图形的轮廓设置效果", 50, 100, mTextPaint);

        canvas.save();
        canvas.translate(0, 100);
        // 第一处：CornerPathEffect
        mPaint.setPathEffect(cornerPathEffect);
        canvas.drawPath(path, mPaint);
        canvas.drawText("CornerPathEffect 所有拐角变成圆角", 50, 250, mTextPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 300);
        // 第二处：DiscretePathEffect
        mPaint.setPathEffect(discretePathEffect);
        canvas.drawPath(path, mPaint);
        canvas.drawText("DiscretePathEffect 把线条进行随机的偏离", 50, 250, mTextPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 500);
        // 第三处：DashPathEffect
        mPaint.setPathEffect(dashPathEffect);
        canvas.drawPath(path, mPaint);
        canvas.drawText("DashPathEffect 使用虚线来绘制线条", 50, 250, mTextPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 700);
        // 第四处：PathDashPathEffect
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(path, mPaint);
        canvas.drawText("PathDashPathEffect  使用一个 Path 来绘制「虚线」", 50, 250, mTextPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 900);
        // 第五处：SumPathEffect
        mPaint.setPathEffect(sumPathEffect);
        canvas.drawPath(path, mPaint);
        canvas.drawText("SumPathEffect 分别按照两种 PathEffect 分别对目标进行绘制", 50, 250, mTextPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 1100);
        mPaint.setPathEffect(composePathEffect);
        // 第六处：ComposePathEffect
        canvas.drawPath(path, mPaint);
        drawText(canvas, "ComposePathEffect 先对目标 Path 使用一个 PathEffect，再对改变后的 Path 使用另一个 PathEffect", 50, 220);
        canvas.restore();
    }

    private void drawText(Canvas canvas, String text, float dx, float dy) {
        canvas.translate(dx, dy);
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(30);
        StaticLayout staticLayout = new StaticLayout(text, textPaint, 800, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout.draw(canvas);
    }
}
