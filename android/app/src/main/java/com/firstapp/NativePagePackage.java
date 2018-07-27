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
 * Created by song on 2018/7/24.
 * email：bjay20080613@qq.com
 */
public class NativePagePackage  implements ReactPackage {
    public NativePageModule mNativePageModule;
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules=new ArrayList<>();
        mNativePageModule=new NativePageModule(reactContext);
        modules.add(mNativePageModule);
        return modules;

    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
