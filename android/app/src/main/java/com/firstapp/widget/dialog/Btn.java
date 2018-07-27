package com.firstapp.widget.dialog;

import android.view.View;

import static com.firstapp.helper.AppHelper.getString;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:21
 */

public class Btn implements IButton {

    private String btn;
    private View.OnClickListener clickListener;

    public Btn(int btn, View.OnClickListener clickListener) {
        this.btn = getString(btn);
        this.clickListener = clickListener;
    }

    public Btn(String btn, View.OnClickListener clickListener) {
        this.btn = btn;
        this.clickListener = clickListener;
    }

    public Btn(int btn) {
        this.btn = getString(btn);
    }


    @Override
    public String getText() {
        return btn;
    }

    @Override
    public View.OnClickListener getListener() {
        return clickListener;
    }
}
