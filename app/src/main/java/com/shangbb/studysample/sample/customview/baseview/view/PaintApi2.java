package com.shangbb.studysample.sample.customview.baseview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.shangbb.studysample.R;

/**
 * @Fuction: Paint : setStrokeCap, setStrokeJoin, setStrokeMiter
 * @Author: BBShang
 * @Date: 2017/9/19 18:09
 */

public class PaintApi2 extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private Bitmap mBitmap;
    //边界模糊效果
    private BlurMaskFilter mBlurMaskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL);
    //边界浮雕效果
    private EmbossMaskFilter mEmbossMaskFilter = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.3f, 50, 80);

    public PaintApi2(Context context) {
        super(context);

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mPaint.setStrokeWidth(40);

        mTextPaint.reset();
        mTextPaint.setTextSize(30);

        path.rLineTo(200, 0);
        path.rLineTo(-160, 120);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.setStrokeCap() 来设置端点形状

        canvas.drawText("Paint.setStrokeCap() 端点形状", 50, 50, mTextPaint);
        // 第一个：BUTT
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(50, 100, 400, 100, mPaint);
        canvas.drawText("BUTT", 450, 110, mTextPaint);

        // 第二个：ROUND
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(50, 200, 400, 200, mPaint);
        canvas.drawText("ROUND", 450, 210, mTextPaint);

        // 第三个：SQUARE
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(50, 300, 400, 300, mPaint);
        canvas.drawText("SQUARE", 450, 310, mTextPaint);

        canvas.save();

        // 使用 Paint.setStrokeJoin() 来设置不同的拐角形状
        canvas.drawText("Paint.setStrokeJoin() 拐角形状", 50, 380, mTextPaint);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.translate(100, 450);
        // 第一种形状：MITER
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setStrokeMiter(4);
        canvas.drawPath(path, mPaint);
        canvas.drawText("MITER", 100, 150, mTextPaint);

        canvas.translate(300, 0);
        // 第二种形状：BEVEL
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);
        canvas.drawText("BEVEL", 100, 150, mTextPaint);

        canvas.translate(300, 0);
        // 第三种形状：ROUND
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);
        canvas.drawText("ROUND", 100, 150, mTextPaint);

        canvas.restore();

        // 使用 Paint.setShadowLayer() 设置阴影
        mTextPaint.reset();
        mTextPaint.setTextSize(80);
        mTextPaint.setShadowLayer(10, 0, 0, Color.RED);
        canvas.drawText("setShadowLayer", 50, 800, mTextPaint);

        // 用 Paint.setMaskFilter 来设置 BlurMaskFilter, 需关闭硬件加速
        mPaint.reset();
        mPaint.setMaskFilter(mBlurMaskFilter);
        canvas.drawBitmap(mBitmap, 50, 1000, mPaint);

        mPaint.reset();
        mPaint.setMaskFilter(mEmbossMaskFilter);
        canvas.drawBitmap(mBitmap, 500, 1000, mPaint);
    }
}
