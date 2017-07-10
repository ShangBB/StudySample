package com.shangbb.studysample.sample.customview.recordview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.shangbb.studysample.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Fuction: 自定义录音、播放动画，带波浪效果
 * @Author: BBShang
 * @Date: 2017/4/11 14:26
 * <p>
 * http://blog.csdn.net/qq_25867141/article/details/53116545
 */

public class RecordView extends View {

    private final String TAG = "RecordView";

    //View默认最小宽度
    private static final int DEFAULT_MIN_WIDTH = 500;
    public final static int MODEL_RECORD = 1;
    public final static int MODEL_PLAY = 2;
    private final TypedArray typedArray;
    private int pandding = 10; //圆环的边距
    private int widthing = 5;//圆环的宽度

    private Context mContext;
    private Paint mPaint;
    private int totalTime = 9;//倒计时总时间，默认时间10秒
    private int countdownTime = 9;//倒计时剩余时间，默认时间10秒.这是会变的
    private float progress = 0;//总进度360
    private boolean canDrawProgress = false;
    private double r;
    private Timer timeTimer = new Timer(true);
    private Timer progressTimer = new Timer(true);
    private long lastTime = 0;
    private int lineSpeed = 100;
    private float translateX = 0;
    /**
     * 圆环颜色
     */
    private int[] doughnutColors = new int[]{0xFFDAF6FE, 0xFF45C3E5, 0xFF45C3E5, 0xFF45C3E5,
            0xFF45C3E5};
    /**
     * 默认是录制模式
     */
    private int model = MODEL_RECORD;
    /**
     * 计时器提示时间
     */
    private String hintText = "";
    /**
     * 进度条终点图片
     */
    private Drawable progressDrawable;
    /**
     * 振幅
     */
    private float amplitude = 1;
    /**
     * 音量
     */
    private float volume = 10;
    private int fineness = 1;
    private float targetVolume = 1;
    private float maxVolume = 100;
    private boolean isSet = false;
    /**
     * 灵敏度
     */
    private int sensibility = 4;
    private boolean canSetVolume = true;

    private TimerTask timeTask;
    private TimerTask progressTask;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                countdownTime--;
                if (countdownTime == 0) {
                    listener.onCountDown();
                    canSetVolume = false;
                    timeTask.cancel();
                    postInvalidate();
                }
            } else if (msg.what == 2) {
                progress += 360.00 / (totalTime * 950.00 / 5.00);
                if (progress > 360) {
                    targetVolume = 1;
                    postInvalidate();
                    progressTask.cancel();
                } else {
                    postInvalidate();
                }
            }
        }
    };
    private OnCountDownListener listener;
    private float textHintSize;
    private float middleLineHeight;
    private int middleLineColor;
    private int voiceLineColor;
    private ArrayList<Path> paths;
    private int timeTextColor;
    private String unit;
    private String playHintText;

    public RecordView(Context context) {
        this(context, null);
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.recordView);
        initAtts();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private void initAtts() {
        model = typedArray.getInt(R.styleable.recordView_model, MODEL_RECORD);
        hintText = typedArray.getString(R.styleable.recordView_hintText);
        progressDrawable = typedArray.getDrawable(R.styleable.recordView_progressSrc) == null ?
                getResources().getDrawable(R.mipmap.light_blue) : typedArray.getDrawable(R
                .styleable.recordView_progressSrc);
        textHintSize = typedArray.getDimension(R.styleable.recordView_hintTextSize, 15);
        middleLineColor = typedArray.getColor(R.styleable.recordView_middleLineColor,
                getResources().getColor(R.color.round_fill_color));
        voiceLineColor = typedArray.getColor(R.styleable.recordView_middleLineColor, getResources
                ().getColor(R.color.round_fill_color));
        middleLineHeight = typedArray.getDimension(R.styleable.recordView_middleLineHeight, 2);
        timeTextColor = typedArray.getColor(R.styleable.recordView_timeTextColor, getResources()
                .getColor(R.color.time_text_color));
        unit = typedArray.getString(R.styleable.recordView_unit);
        playHintText = typedArray.getString(R.styleable.recordView_playHintText);
        paths = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            paths.add(new Path());
        }
    }

    /**
     * 当布局为wrap_content时设置默认长宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
    }

    //MeasureSpec.EXACTLY是精确尺寸，
    //  当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，
    //  或者为FILL_PARENT时，都是控件大小已经确定的情况，都是精确尺寸。

    //MeasureSpec.AT_MOST是最大尺寸，
    //  当控件的layout_width或layout_height指定为WRAP_CONTENT时，控件大小一般随着控件的子空间或内容进行变化，
    //  此时控件尺寸只要不超过父控件允许的最大尺寸即可。因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。

    //MeasureSpec.UNSPECIFIED是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
    private int measure(int origin) {
        int result = DEFAULT_MIN_WIDTH;
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (model == MODEL_RECORD) { //录制
            drawDefaultView(canvas);
            drawVoiceLine(canvas);
        } else {  //播放
            drawDefaultForPlay(canvas);
            drawVoiceLinePlay(canvas);
        }
        //这边开启画进度条
        if (canDrawProgress) {
            drawProgress(canvas);
        }
    }

    private void drawDefaultForPlay(Canvas canvas) {

        drawArcSolid(canvas);

        /**
         * 画时间
         * */
        Paint paintCountDown = new Paint(Paint.ANTI_ALIAS_FLAG); //创建抗锯齿的Paint
        paintCountDown.setTextSize(dip2px(mContext, 14));
        paintCountDown.setColor(mContext.getResources().getColor(R.color.round_fill_color));
        paintCountDown.setTextAlign(Paint.Align.CENTER);//设置文本对齐方式
        if (TextUtils.isEmpty(playHintText)) {
            playHintText = "正在播放录音.";
        }
        canvas.drawText(playHintText, getWidth() / 2, getHeight() / 3, paintCountDown);
    }

    /**
     * 画一个大圆(纯色)
     */
    private void drawArcSolid(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔为空心
        mPaint.setStrokeWidth(dip2px(mContext, widthing)); //设置画笔的空心线宽
        mPaint.setColor(mContext.getResources().getColor(R.color.round_color));
        RectF oval = new RectF(dip2px(mContext, pandding)
                , dip2px(mContext, pandding)
                , getWidth() - dip2px(mContext, pandding)
                , getHeight() - dip2px(mContext, pandding));
        canvas.drawArc(oval, 0, 360, false, mPaint);    //绘制圆弧
    }

    private void drawDefaultView(Canvas canvas) {

        drawArcSolid(canvas);

        /**
         * 画提示的文字
         * */
        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextSize(dip2px(mContext, textHintSize));
        paintText.setColor(mContext.getResources().getColor(R.color.round_hint_text_color));
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paintText.setTextAlign(Paint.Align.CENTER);
        if (TextUtils.isEmpty(hintText)) {
            hintText = "剩余录制时间";
        }
        canvas.drawText(hintText, getWidth() / 2, getHeight() / 2 + 50, paintText);

        /**
         * 画时间
         * */
        Paint paintTime = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTime.setTextSize(dip2px(mContext, 50));
        paintTime.setColor(mContext.getResources().getColor(R.color.time_text_color));
        paintTime.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(String.valueOf(countdownTime), getWidth() / 2, getHeight() / 2 - 20,
                paintTime);

        /**
         * 画单位，默认s
         * */
        Paint paintTimeUnit = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintTimeUnit.setTextSize(dip2px(mContext, 40));
        paintTimeUnit.setColor(mContext.getResources().getColor(R.color.time_text_color));
        paintTimeUnit.setTextAlign(Paint.Align.LEFT);
        float timeWidth = paintTime.measureText(String.valueOf(countdownTime));
        canvas.drawText(unit == null ? "s" : unit,
                getWidth() / 2f + timeWidth / 2 + 10, getHeight() / 2 - 20, paintTimeUnit);

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 绘制圆弧
     */
    private void drawProgress(Canvas canvas) {

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(mContext, widthing));
        RectF oval = new RectF(dip2px(mContext, pandding)
                , dip2px(mContext, pandding)
                , getWidth() - dip2px(mContext, pandding)
                , getHeight() - dip2px(mContext, pandding));

        /**
         * 大于90的位置度画纯色圆弧
         */
        if (progress > 90) {
            mPaint.setColor(getResources().getColor(R.color.round_fill_color));
            canvas.drawArc(oval, 0, progress - 90, false, mPaint);    //绘制圆弧
        }
        /**
         * 小于90的位置度画渐变圆弧
         */
        canvas.rotate(-90, getWidth() / 2, getHeight() / 2);
        //设置图像效果，使用Shader可以绘制出各种渐变效果
        mPaint.setShader(new SweepGradient(getWidth() / 2, getHeight() / 2, doughnutColors, null));
        //看这里，其实这里当progress大于90以后就一直只画0-90度的圆环
        canvas.drawArc(oval, 0, progress < 90 ? progress : 90, false, mPaint);    //绘制圆弧
        canvas.rotate(90, getWidth() / 2, getHeight() / 2);
        mPaint.reset();

        drawImageDot(canvas);
    }

    /**
     * 画一个点（图片）
     */
    private void drawImageDot(Canvas canvas) {
        r = getHeight() / 2f - dip2px(mContext, pandding); //半径
        if (r > 0) {
            if (progress > 360)
                return;
            double hu = Math.PI * Double.parseDouble(String.valueOf(progress)) / 180.0; //弧度=角度*
            // (π/180°）
            double p = Math.sin(hu) * r;
            double q = Math.cos(hu) * r;
            float x = (float) ((getWidth() - progressDrawable.getIntrinsicWidth()) / 2f + p);
            float y = (float) ((dip2px(mContext, pandding) - progressDrawable.getIntrinsicHeight
                    () / 2f) + r - q);
            canvas.drawBitmap(((BitmapDrawable) progressDrawable).getBitmap(), x, y, mPaint);
        }
    }


    /**
     * 画声纹(录制)
     */
    private void drawVoiceLine(Canvas canvas) {
        lineChange();
        mPaint.setColor(voiceLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        canvas.save();
        int moveY = getHeight() * 3 / 4;
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).reset();
            paths.get(i).moveTo(getWidth() * 5 / 6, getHeight() * 3 / 4);
        }
        for (float j = getWidth() * 5 / 6 - 1; j >= getWidth() / 6; j -= fineness) {
            float i = j - getWidth() / 6;
            //这边必须保证起始点和终点的时候amplitude = 0;
            amplitude = 5 * volume * i / getWidth() - 5 * volume * i / getWidth() * i / getWidth
                    () * 6 / 4;
            for (int n = 1; n <= paths.size(); n++) {
                float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180
                        - translateX);
                paths.get(n - 1).lineTo(j, (2 * n * sin / paths.size() - 15 * sin / paths.size()
                        + moveY));
            }
        }
        for (int n = 0; n < paths.size(); n++) {
            if (n == paths.size() - 1) {
                mPaint.setAlpha(255);
            } else {
                mPaint.setAlpha(n * 130 / paths.size());
            }
            if (mPaint.getAlpha() > 0) {
                canvas.drawPath(paths.get(n), mPaint);
            }
        }
        canvas.restore();
    }

    /**
     * 画声纹（播放）
     */
    private void drawVoiceLinePlay(Canvas canvas) {
        lineChange();
        mPaint.setColor(voiceLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.save();
        int moveY = getHeight() / 2;
        int pandY = getWidth() / 12;

        //确定声波水平线的位置
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).reset();
            paths.get(i).moveTo(getWidth() - pandY, getHeight() / 2);
        }

        //画声波
        for (float j = getWidth() * 11 / 12 - 1; j >= getWidth() / 12; j -= fineness) {
            float i = j - getWidth() / 12;
            //这边必须保证起始点和终点的时候amplitude = 0;
            amplitude = 4 * volume * i / getWidth() - 4 * volume * i / getWidth() * i / getWidth
                    () * 12 / 10;
            for (int n = 1; n <= paths.size(); n++) {
                float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180
                        - translateX);
                paths.get(n - 1).lineTo(j, (2 * n * sin / paths.size() - 15 * sin / paths.size()
                        + moveY));
            }
        }

        //颜色的渐变
        for (int n = 0; n < paths.size(); n++) {
            if (n == paths.size() - 1) {
                mPaint.setAlpha(255);
            } else {
                mPaint.setAlpha(n * 130 / paths.size());
            }
            if (mPaint.getAlpha() > 0) {
                canvas.drawPath(paths.get(n), mPaint);
            }
        }
        canvas.restore();
    }

    private void lineChange() {
        if (lastTime == 0) {
            lastTime = System.currentTimeMillis();
            translateX += 5;
        } else {
            if (System.currentTimeMillis() - lastTime > lineSpeed) {
                lastTime = System.currentTimeMillis();
                translateX += 5;
            } else {
                return;
            }
        }
        if (volume < targetVolume && isSet) {
            volume += getHeight() / 30;
        } else {
            isSet = false;
            if (volume <= 10) {
                volume = 10;
            } else {
                if (volume < getHeight() / 30) {
                    volume -= getHeight() / 60;
                } else {
                    volume -= getHeight() / 30;
                }
            }
        }
    }

    /**
     * ==================================PUBLIC===========================================
     */

    public void setModel(int model) {
        this.model = model;
        postInvalidate();
    }


    public void start() {
        //重置计时器显示的时间
        canSetVolume = true;
        canDrawProgress = true;
        progress = 0;
        countdownTime = totalTime;
        //启动定时器
        timeTimer.schedule(timeTask = new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }
        }, 1000, 1000);
        progressTimer.schedule(progressTask = new TimerTask() {
            public void run() {
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        }, 0, 5);
    }

    public void cancel() {
        listener.onCountDown();
        canSetVolume = false;
        timeTask.cancel();
        targetVolume = 1;
        postInvalidate();
        progressTask.cancel();
    }

    public void setVolume(int volume) {
        if (volume > 100)
            volume = volume / 100;
        volume = volume * 2 / 5;
        if (!canSetVolume)
            return;
        if (volume > maxVolume * sensibility / 30) {
            isSet = true;
            this.targetVolume = getHeight() * volume / 3 / maxVolume;
            Log.d(TAG, "targetVolume: " + targetVolume);
        }
    }

    public void setCountDownTime(int totalTime) {
        this.countdownTime = totalTime;
        this.totalTime = totalTime;
        postInvalidate();
    }


    interface OnCountDownListener {
        void onCountDown();
    }

    public void setOnCountDownListener(OnCountDownListener listener) {
        this.listener = listener;
    }


}
