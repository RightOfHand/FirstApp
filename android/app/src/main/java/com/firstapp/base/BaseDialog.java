package com.firstapp.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:03
 */
public abstract class BaseDialog extends Dialog implements IDialog {

    public BaseDialog(Context context) {
        super(context);
        init();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected BaseDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init(){
        beforeBindView();
        setContentView(getRootLayoutId());
        bindView();
        afterBindView();
    }

    @LayoutRes
    public abstract int getRootLayoutId();

    public void beforeBindView(){

    }

    public abstract void bindView();

    public abstract void afterBindView();
}
