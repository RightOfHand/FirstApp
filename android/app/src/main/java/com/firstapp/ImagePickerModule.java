package com.firstapp;

import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Description:
 * Created by song on 2018/7/3.
 * email：bjay20080613@qq.com
 */
public class ImagePickerModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext mContext;
    public static final int REQUEST_CODE_CAMERA = 0xa00;  //照相的request_code
    public static final int REQUEST_CODE_IMAGE = 0xa01;   //相册的request_code
    private Promise promise;

    public ImagePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext=reactContext;

    }

    @Override
    public String getName() {
        return "ImagePickerModule";
    }

//    //1.RCTDeviceEventEmitter
//    @ReactMethod
//    public void pickImage(String msg) {
//        final Activity currentActivity = getCurrentActivity();
//        Log.d("ACCEPT",msg);
//        if (currentActivity == null) {
//            return;
//        }
//
//        if (currentActivity instanceof MainActivity){
//            ((MainActivity) currentActivity).setSelect();
//        }
//
//    }

    //native
    @ReactMethod
    public void pickImage(String msg, Promise promise) {
        final Activity currentActivity = getCurrentActivity();
        Log.d("ACCEPT",msg);
        this.promise=promise;
        if (currentActivity == null) {
            return;
        }

        if (currentActivity instanceof MainActivity){
            ((MainActivity) currentActivity).setSelect();
        }

    }
    
    //1.React Native的RCTDeviceEventEmitter 数据传递
    public void sendMsgToRn(String msg){
        //将消息msg发送给RN侧
        mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("AndroidToRNMessage",msg);

    }

    //2.Promise
    public void sendMsgToJS(String msg){
        promise.resolve(msg);
    }




}
