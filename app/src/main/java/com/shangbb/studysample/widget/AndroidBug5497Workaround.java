package com.shangbb.studysample.widget;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Workaround to get adjustResize functionality for input methos when the fullscreen mode is on
 * found by Ricardo taken from http://stackoverflow.com/a/19494006
 *
 * Android全屏和adjustResize的冲突解决
 *
 * 使用方法:在你的Activity的oncreate()方法里调用AndroidBug5497Workaround.assistActivity(this);即可。
 * 注意：在setContentView(R.layout.xxx)之后调用。
 */
public class AndroidBug5497Workaround {
    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its
    // content view set.

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private Activity activity;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity) {
        this.activity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();

            //这个判断是为了解决19之前的版本不支持沉浸式状态栏导致布局显示不完全的问题
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                Rect frame = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;
                usableHeightSansKeyboard -= statusBarHeight;
            }
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);

        //这个判断是为了解决19之后的版本在弹出软键盘时，键盘和推上去的布局（adjustResize）之间有黑色区域的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (r.bottom - r.top) + statusBarHeight;
        }

        return (r.bottom - r.top);
    }
}
