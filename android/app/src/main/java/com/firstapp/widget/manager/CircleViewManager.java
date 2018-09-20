package com.firstapp.widget.manager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.firstapp.widget.CircleView;

import java.util.Map;

import javax.annotation.Nullable;

/**
 * Description:
 * Created by song on 2018/7/23.
 * email：bjay20080613@qq.com
 */
public class CircleViewManager extends SimpleViewManager<CircleView> implements LifecycleEventListener {
    private static final String EVENT_NAME_ONCLICK = "onClick";
    private static final String HANDLE_METHOD_NAME = "handleTask"; // 交互方法名
    private static final int HANDLE_METHOD_ID = 1; // 交互命令ID
    private ThemedReactContext mContext;
    /**
     * 设置js引用名
     */
    @Override
    public String getName() {
        return "MCircle";
    }

    /**
     * 创建UI组件实例
     */
    @Override
    protected CircleView createViewInstance(ThemedReactContext reactContext) {
        this.mContext = reactContext;
        this.mContext.addLifecycleEventListener(this);
        final CircleView circleView = new CircleView(reactContext);
         circleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WritableMap data = Arguments.createMap();
                data.putString("msg","native UI被点了");
                mContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                        circleView.getId(), // RN层原生层根据id绑定在一起
                        EVENT_NAME_ONCLICK, // 事件名称
                        data // 传递的数据
                );
            }
        });

        return circleView;
    }

    /**
     * 设置背景色
     */
    @ReactProp(name = "color")
    public void setColor(CircleView view, Integer color) {
        view.setColor(color);
    }


    /**
     * 设置半径
     */
    @ReactProp(name = "radius")
    public void setRadius(CircleView view, Integer radius) {
        view.setRadius(radius);
    }


    /**
     * 自定义事件
     */
    @Nullable
    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(EVENT_NAME_ONCLICK, MapBuilder.of("registrationName",EVENT_NAME_ONCLICK));
    }

    /**
     * 接收交互通知
     * */
    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(HANDLE_METHOD_NAME,HANDLE_METHOD_ID);
    }

    /**
     * 处理通知
     * */
    @Override
    public void receiveCommand(CircleView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId){
            case HANDLE_METHOD_ID:
                Log.d("ACCEPT========","come here");
                Toast.makeText(mContext,"RN层任务通知",Toast.LENGTH_LONG).show();
                if (args!=null){
//                    String message=args.getString(0); //获取
//                    Toast.makeText(mContext,"RN层任务通知",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {

    }
}
