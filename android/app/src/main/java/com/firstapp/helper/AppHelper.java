package com.firstapp.helper;

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
}
