package com.firstapp;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.firstapp.activity.FirstNativeActivity;
import com.firstapp.constant.ExtraKeys;
import com.firstapp.widget.pop.TextListPop;

/**
 * Description:
 * Created by song on 2018/7/24.
 * email：bjay20080613@qq.com
 */
public class NativePageModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    private static long mCurrentTime=System.currentTimeMillis();
    public NativePageModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext=reactContext;

    }
    @Override
    public String getName() {
        return "NativePageModule";
    }

    @ReactMethod
    public void toNative(String param){
        final Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            return;
        }
        Intent intent=new Intent(currentActivity,FirstNativeActivity.class);
        intent.putExtra(ExtraKeys.EXTRA_KEY,param);
        currentActivity.startActivity(intent);
    }
}
