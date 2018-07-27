package com.firstapp.service;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import javax.annotation.Nullable;

/**
 * Description:
 * Created by song on 2018/7/19.
 * email：bjay20080613@qq.com
 */
public class MyTaskService extends HeadlessJsTaskService {
    @Nullable
    @Override
    protected HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            return new HeadlessJsTaskConfig(
                    "HeadlessTest",
                    Arguments.fromBundle(extras),
                    5000, // 任务的超时时间
                    false // 可选参数：是否允许任务在前台运行，默认为false
            );
        }
        return null;
    }
}
