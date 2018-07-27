package com.firstapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.facebook.react.uimanager.PixelUtil;

/**
 * Description:
 * Created by song on 2018/7/23.
 * email：bjay20080613@qq.com
 */
public class CircleView extends View {
    private final String TAG = "CircleView";
    private Paint mPaint; // 画笔
    private float mRadius;

    public CircleView(Context context) {
        super(context);
        mPaint = new Paint();
    }
    /**
     * 设置圆的半径
     * @param color
     */
    public void setColor(Integer color) {
        mPaint.setColor(color); // 设置画笔颜色
        invalidate();   // 更新画板
    }

    /**
     * 设置圆的半径
     * @param radius
     */
    public void setRadius(Integer radius) {
        mRadius = PixelUtil.toPixelFromDIP(radius);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint); // 画一个半径为100px的圆
        Log.d(TAG, "绘图");
    }


    public void onReciveNativeEvent(){

    }
}
