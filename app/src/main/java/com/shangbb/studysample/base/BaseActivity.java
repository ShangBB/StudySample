package com.shangbb.studysample.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.shangbb.studysample.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @Fuction: Activity 基类
 * @Author: Shang
 * @Date: 2016/4/18  17:16
 */
public class BaseActivity extends AppCompatActivity
        implements  OnClickListener{
    protected Context mContext;
    protected Toolbar toolbar;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        loadingDialog = createLoadingDialog(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        processIntent();
        initToolBar();
        initViews();
        initListener();
    }

    /**
     * @Title initToolBar
     * @Description 子activity 覆盖这个方法初始化toolbar
     */
    protected void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected void initBack() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * @Title initViews
     * @Description 子activity 覆盖这个方法初始化ui控件
     */
    protected void initViews() {
    }

    /**
     * @Title initListener
     * @Description 子activity 覆盖这个方法初始化ui控件的监听事件
     */
    protected void initListener() {
    }


    /**
     * @param v
     *         被点击的view
     *
     * @Title onViewClick
     * @Description 子类在这里面捕获控件的点击事件
     */
    protected void onViewClick(View v) {
    }

    @Override
    public void onClick(View v) {
        onViewClick(v);

    }

    /**
     * @Title processIntent
     * @Description 获取Intent携带数据
     */
    protected void processIntent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }



    /*********************************权限适配23up*********************************/

    private static PermissionListener mPermissionListener;

    public static void requestRuntimePermission(String[] permissions, PermissionListener listener){

        Activity topActivity = ActivityCollector.getTopActivity();

        if (topActivity == null){
            return;
        }

        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topActivity, permission) != PackageManager.PERMISSION_GRANTED){
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(topActivity, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length ; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                    }

                    if (deniedPermissions.isEmpty()){
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;

            default:
                break;
        }
    }

    /***************************** 工具方法 **************************/

    /**
     * 通过类名启动Activity
     */
    protected void openActivity(Context context, Class<?> pClass) {
        openActivity(context, pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     */
    protected void openActivity(Context context, Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(context, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    protected Dialog createLoadingDialog(Context context) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null); // 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view); // 加载布局
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog); // 创建自定义样式dialog
        loadingDialog.setCancelable(true); // 是否可以用"返回键"取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

    protected void showLoadingDialog(){
        loadingDialog.show();
    }

    protected void dismissLoadingDialog(){
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }

    }
}

