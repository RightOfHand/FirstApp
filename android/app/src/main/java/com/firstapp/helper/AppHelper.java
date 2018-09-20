package com.firstapp.helper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.DimenRes;

import com.firstapp.MainApplication;

/**
 * Description:
 * Created by song on 2018/7/5.
 * email：bjay20080613@qq.com
 */
public class AppHelper {
    /**
     * 资源ID获取String
     */
    public static String getString(int stringId) {
        return MainApplication.gedAppContext().getString(stringId);
    }
    public static int getDimen(@DimenRes int dimen) {
        return MainApplication.gedAppContext().getResources().getDimensionPixelOffset(dimen);
    }

    //版本号
    public static String getPackName(Context context) {
        return getPackageInfo(context).packageName;
    }
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


    public static String getDeviceDevice(){
        return Build.DEVICE;
    }
}
