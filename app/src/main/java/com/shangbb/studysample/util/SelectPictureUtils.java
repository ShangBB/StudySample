package com.shangbb.studysample.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

/**
 * 调用相机或从相册选择图片
 * 注意处理内存泄漏
 * Created by lenovo on 2016/4/30.
 */
public class SelectPictureUtils {

    /***
     *标记用户点击了从照相机获取图片  即拍照
     */
    public static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    /***
     *标记用户点击了从图库中获取图片  即从相册中取
     */
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    /***
     * 返回处理后的图片
     */
    public static final int PHOTO_REQUEST_CUT = 3;// 结果

    /****
     * 调用系统的拍照功能
     * @param context Activity上下文对象
     * @param uri  Uri
     */
    public static void startCameraForResult(Activity context,Uri uri) {
        if (!hasCamera(context)){
            T.showShortToast(context,"找不到可用的相机");
            return;
        }
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", true);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
    }
    /***
     * 调用系统的图库
     * @param context Activity上下文对象
     */
    public static void startPhotoForResult(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    /*****
     * 进行截图
     * @param context Activity上下文对象
     * @param uri  原图Uri
     * @param cutUri 裁剪后保存的图片Uri
     * @param size 剪裁图片的宽高
     */
    public static void startPhotoCutForResult(Activity context,Uri uri, Uri cutUri,int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cutUri);
        intent.putExtra("return-data", false);//设置为不返回数据

        context.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(Activity mActivity) {
        PackageManager packageManager = mActivity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
