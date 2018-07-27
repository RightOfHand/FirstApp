package com.firstapp.base;


import com.facebook.react.ReactActivity;

import javax.annotation.Nullable;


/**
 * Description:
 * Created by song on 2018/7/5.
 * email：bjay20080613@qq.com
 */
public class BaseActivity extends ReactActivity {
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "FirstApp";
    }
}
