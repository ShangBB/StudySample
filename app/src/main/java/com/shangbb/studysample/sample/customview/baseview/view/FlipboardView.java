package com.shangbb.studysample.sample.customview.baseview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.shangbb.studysample.R;

/**
 * @Fuction: 翻页效果
 * @Author: BBShang
 * @Date: 2017/10/19 11:32
 */

public class FlipboardView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();
    int degree;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 180);

    public FlipboardView(Context context) {
        super(context);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image4);

        animator.setDuration(2500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        // 第一遍绘制：左半部分
        canvas.save();
        canvas.clipRect(0, 0, centerX, getHeight());
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        // 第二遍绘制：右半部分
        canvas.save();

        if (degree < 90) {
            canvas.clipRect(centerX, 0, getWidth(), getHeight());
        } else {
            canvas.clipRect(0, 0, centerX, getHeight());
        }
        camera.save();
        camera.setLocation(0, 0, -16);
        camera.rotateY(-degree);
        // 旋转之后把投影移动回来
        canvas.translate(centerX, centerY);
        // 把旋转投影到 Canvas
        camera.applyToCanvas(canvas);
        // 旋转之前把绘制内容移动到轴心（原点）
        canvas.translate(-centerX, -centerY);
        camera.restore();

        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }
}
