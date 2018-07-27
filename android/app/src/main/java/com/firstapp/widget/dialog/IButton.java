package com.firstapp.widget.dialog;

import android.view.View;

import java.io.Serializable;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:21
 */

public interface IButton extends Serializable {
    String getText();
    View.OnClickListener getListener();
}
