package com.firstapp;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * Created by song on 2018/7/3.
 * email：bjay20080613@qq.com
 */
public class ImagePickerPackage implements ReactPackage {
    public ImagePickerModule mPickerModule;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules=new ArrayList<>();
        mPickerModule=new ImagePickerModule(reactContext);
        modules.add(mPickerModule);
        return modules;
    }


    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
