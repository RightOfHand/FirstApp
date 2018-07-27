package com.firstapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.ReactActivity;
import com.firstapp.MainApplication;
import com.firstapp.base.BaseActivity;
import com.firstapp.helper.AppHelper;
import com.firstapp.widget.dialog.Dialogs;


/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午3:46
 */

public class CheckPermissionUtils {

    private final static String[] locPermissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest
            .permission.ACCESS_FINE_LOCATION};//定位权限

    private final static String[] storagePermissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};//存储权限

    private final static String[] cameraPermissions = {Manifest.permission.CAMERA};//相机权限
    private final static String[] phonePermissions = {Manifest.permission.CALL_PHONE};//相机权限
    private final static String[] necessaryPermissions = {Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE};//必要权限

    private static final int REQUEST_LOCATION_PERMISSION = 998;//尽可能与页面的回调区分
    private static final int REQUEST_STORAGE_PERMISSION = 995;
    private static final int REQUEST_CAMERA_PERMISSION = 997;
    private static final int REQUEST_PHONE_PERMISSION = 993;

    public interface PermissionRequestCallback {
        void hasPermission();

        void noPermission();
    }

    /**
     * 通用检查权限方法
     *
     * @param context               BaseActivity
     * @param permissions           权限数组
     * @param requestPermissionCode 请求权限时的activity 请求码
     * @param permissionName        权限中文名称
     * @param callback              权限回调
     */
    public static void checkPermission(final BaseActivity context, final String[] permissions, final int requestPermissionCode, final String permissionName, final PermissionRequestCallback callback) {
        if (hasPermission(context, permissions)) {
            Log.d("检查结果：有%s权限", permissionName);
            callback.hasPermission();
        } else {
            Log.d("检查结果：没有%s权限", permissionName);
            final String appLabel = AppHelper.getString(MainApplication.gedAppContext().getApplicationInfo().labelRes);
            Log.d("应用名称：" , appLabel);
//            context.addRequestPermissionsResult(new BaseActivity.PermissionCallBack() {
//                @Override
//                public void onRequestPermissionsResult(int requestCode, @NonNull String[]
//                        permissions, @NonNull int[] grantResults) {
//
//                    if (requestCode == requestPermissionCode && grantResults != null && grantResults.length > 0) {
//                        if (checkGrantResult(grantResults, permissions.length)) {
//                            Log.d("请求权限结果：有%s权限", permissionName);
//                            callback.hasPermission();
//                        } else {
//                            Toast.makeText(context,String.format("%s%s权限被禁止,请打开权限", appLabel, permissionName),Toast.LENGTH_LONG).show();
//                            callback.noPermission();
//                            Log.d("请求权限结果：没有%s权限", permissionName);
//                        }
//                    }
//                }
//            });

            if (shouldShowRequestPermissionRationale(context, permissions)) {//给用户一个解释
                Dialogs.showMustConfirmDialog(context, "权限申请", String.format("%s需要获得%s权限才能正常使用此功能,请允许！", appLabel, permissionName), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(context, permissions, requestPermissionCode);
                    }
                });

            } else {
                //请求权限
                ActivityCompat.requestPermissions(context, permissions, requestPermissionCode);
            }
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity context, String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(Activity context, String[] permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(context, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查权限结果
     *
     * @param grantResults    权限列表
     * @param permissionCount 申请权限数
     */
    private static boolean checkGrantResult(int[] grantResults, int permissionCount) {
        for (int i = 0; i < permissionCount; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查定位
     * @param callback 权限检查回掉
     * @param context 数据
     * */
    public static void checkLocPermission(final BaseActivity context, final PermissionRequestCallback callback) {
        checkPermission(context, locPermissions, REQUEST_LOCATION_PERMISSION, "定位", callback);

    }

    public static void checkStoragePermission(final BaseActivity context, final PermissionRequestCallback callback) {
        checkPermission(context, storagePermissions, REQUEST_STORAGE_PERMISSION, "存储", callback);
    }

    public static void checkCameraPermission(final BaseActivity context, final PermissionRequestCallback callback) {

        checkPermission(context, cameraPermissions, REQUEST_CAMERA_PERMISSION, "相机", callback);
    }

    public static void checkPhonePermission(final BaseActivity context, final PermissionRequestCallback callback) {
        checkPermission(context, phonePermissions, REQUEST_PHONE_PERMISSION, "拨打电话", callback);
    }

    public static void checkNecessaryPermissions(final BaseActivity context, final PermissionRequestCallback callback) {
        checkPermission(context, necessaryPermissions, REQUEST_PHONE_PERMISSION, "必要", callback);
    }

    /**
     * 是否有获取位置的权限
     *
     * @param context Activity
     */
    public static boolean hasLocationPermission(Activity context) {
        return hasPermission(context, locPermissions);
    }

    /**
     * 是否有相机权限
     *
     * @param context Activity
     */
    public static boolean hasCameraPermission(Activity context) {
        return hasPermission(context, cameraPermissions);
    }

    /**
     * 是否有存储权限
     *
     * @param context Activity
     */
    public static boolean hasStoragePermission(Activity context) {
        return hasPermission(context, storagePermissions);
    }

    /**
     * 是否有电话权限
     *
     * @param context Activity
     */
    public static boolean hasPhonePermission(Activity context) {
        return hasPermission(context, phonePermissions);
    }
}
