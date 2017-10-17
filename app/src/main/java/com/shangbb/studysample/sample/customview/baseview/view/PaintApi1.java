package com.shangbb.studysample.sample.customview.baseview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.view.View;

import com.shangbb.studysample.R;

/**
 * @Fuction: Paint : setShader, setColorFilter, setXfermode
 * @Author: BBShang
 * @Date: 2017/9/19 18:09
 */

public class PaintApi1 extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private LinearGradient mLineargradient;
    private RadialGradient mRadialGradient;
    private SweepGradient mSweepGradient;
    private BitmapShader mBitmapShader;
    private ComposeShader mComposeShader;
    private Bitmap mBitmap, mBitmap1, mBitmap2;
    private LightingColorFilter mLightingColorFilter1, mLightingColorFilter2;
    private PorterDuffColorFilter mPorterDuffColorFilter;
    private ColorMatrixColorFilter mColorMatrixColorFilter;
    private Xfermode xfermode1, xfermode2, xfermode3;

    public PaintApi1(Context context) {
        super(context);
        mLineargradient = new LinearGradient(100, 600, 300, 800,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.MIRROR);

        mRadialGradient = new RadialGradient(450, 700, 100,
                Color.parseColor("#E91E63"), Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

        mSweepGradient = new SweepGradient(700, 700, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));

        mBitmapShader = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.image4),
                Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);

        BitmapShader bitmapShader1 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.image2),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        BitmapShader bitmapShader2 = new BitmapShader(BitmapFactory.decodeResource(getResources(), R.drawable.ic_d_1),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mComposeShader = new ComposeShader(bitmapShader1, bitmapShader2, PorterDuff.Mode.DST_OUT);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_d_2);

        //模拟简单的光照效果,参数里的 mul 和 add 都是和颜色值格式相同的 int 值，其中 mul 用来和目标像素相乘，add 用来和目标像素相加
        mLightingColorFilter1 = new LightingColorFilter(0x00ffff, 0x000000);
        mLightingColorFilter2 = new LightingColorFilter(0x00ffff, 0x003000);

        //使用一个指定的颜色和一种指定的 PorterDuff.Mode 来与绘制对象进行合成
        mPorterDuffColorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

        //使用一个 ColorMatrix 来对颜色进行处理
        ColorMatrix colorMatrix = new ColorMatrix();
        //设置饱和度
        colorMatrix.setSaturation(0);
        mColorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_d_1);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        xfermode1 = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        xfermode2 = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        xfermode3 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 用 Paint.setShader(shader) 设置一个 BitmapShader
        mPaint.reset();
        mPaint.setShader(mBitmapShader);
        canvas.drawRect(0, 0, 1200,1800, mPaint);

        // 用 Paint.setShader(shader) 设置一个 ComposeShader
        mPaint.reset();
        mPaint.setShader(mComposeShader);
        canvas.drawRect(0, 0, 1200,600, mPaint);

        // 用 Paint.setShader(shader) 设置一个 LinearGradient
        mPaint.reset();
        mPaint.setShader(mLineargradient);
        canvas.drawCircle(200, 700, 100, mPaint);

        // 用 Paint.setShader(shader) 设置一个 RadialGradient
        mPaint.reset();
        mPaint.setShader(mRadialGradient);
        canvas.drawCircle(450, 700, 100, mPaint);

        // 用 Paint.setShader(shader) 设置一个 SweepGradient
        mPaint.reset();
        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(700, 700, 100, mPaint);

        //原图
        mPaint.reset();
        canvas.drawBitmap(mBitmap, 0, 800, mPaint);

        // 使用 Paint.setColorFilter() 来设置 LightingColorFilter
        // 第一个 LightingColorFilter：去掉红色部分
        mPaint.reset();
        mPaint.setColorFilter(mLightingColorFilter1);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() - 100, 800, mPaint);

        // 第二个 LightingColorFilter：增强绿色部分
        mPaint.reset();
        mPaint.setColorFilter(mLightingColorFilter2);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() * 2 - 200, 800, mPaint);

        // 使用 setColorFilter() 设置一个 PorterDuffColorFilter
        mPaint.reset();
        mPaint.setColorFilter(mPorterDuffColorFilter);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() * 3 - 300, 800, mPaint);

        // 使用 setColorFilter() 设置一个 ColorMatrixColorFilter
        // 用 ColorMatrixColorFilter.setSaturation() 把饱和度去掉
        mPaint.reset();
        mPaint.setColorFilter(mColorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() * 4 - 400, 800, mPaint);


        // 使用 paint.setXfermode() 设置不同的结合绘制效果
        mPaint.reset();
        // 别忘了用 canvas.saveLayer() 开启 off-screen buffer
        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBitmap1, 0, 1300, mPaint);
        // 第一个：PorterDuff.Mode.SRC
        mPaint.setXfermode(xfermode1);
        canvas.drawBitmap(mBitmap2, 0, 1300, mPaint);
        mPaint.setXfermode(null);

        canvas.drawBitmap(mBitmap1, mBitmap1.getWidth(), 1300, mPaint);
        // 第二个：PorterDuff.Mode.DST_IN
        mPaint.setXfermode(xfermode2);
        canvas.drawBitmap(mBitmap2, mBitmap1.getWidth(), 1300, mPaint);
        mPaint.setXfermode(null);

        canvas.drawBitmap(mBitmap1, mBitmap1.getWidth() * 2, 1300, mPaint);
        // 第三个：PorterDuff.Mode.DST_OUT
        mPaint.setXfermode(xfermode3);
        canvas.drawBitmap(mBitmap2, mBitmap1.getWidth() * 2, 1300, mPaint);
        mPaint.setXfermode(null);

        // 用完之后使用 canvas.restore() 恢复 off-screen buffer
        canvas.restoreToCount(saved);

    }
}
