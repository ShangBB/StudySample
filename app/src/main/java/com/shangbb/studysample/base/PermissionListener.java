package com.shangbb.studysample.base;

import java.util.List;

/**
 * 权限管理 回调接口
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermisssion);
}
