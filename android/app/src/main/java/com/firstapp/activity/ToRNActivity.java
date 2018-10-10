package com.firstapp.activity;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactInstanceManagerBuilder;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.firstapp.BuildConfig;
import com.firstapp.base.MyReactActivity;

import java.io.File;

/**
 * Description:原生页->RN页
 * Created by song on 2018/7/30.
 * email：bjay20080613@qq.com
 */
public class ToRNActivity extends MyReactActivity {
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setJSMainModulePath("index")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        // 注意这里的MyReactNativeApp必须对应“index.js”中的
        // “AppRegistry.registerComponent()”的第一个参数
        mReactRootView.startReactApplication(mReactInstanceManager, "FirstApp", null);

        setContentView(mReactRootView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReactInstanceManager!=null){
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mReactInstanceManager!=null){
            mReactInstanceManager.onHostResume(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReactInstanceManager!=null){
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView!=null){
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager!=null){
            mReactInstanceManager.onBackPressed();
        }else {
            super.onBackPressed();
        }

    }
}
