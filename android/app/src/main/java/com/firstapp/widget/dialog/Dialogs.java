package com.firstapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.firstapp.R;

import java.util.List;

/**
 * Description:
 * Created by song on 2018/7/5.
 * email：bjay20080613@qq.com
 */
public class Dialogs {

    public static Dialog showMustConfirmDialog(final Context context, String title, String message, final View.OnClickListener listener) {
        final CommonDialog dialog = new CommonDialog(context, R.style.Dialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle(title);
        dialog.setContent(message);
        dialog.setGravity(Gravity.CENTER);
        dialog.setTitleGravity(Gravity.CENTER);
        dialog.setButtons(new Btn(R.string.ok, listener));
        dialog.show();
        return dialog;
    }

    public static <T> Dialog showListDialog(Context context, String title, final List<T> content,
                                            final OnSelectListener<T> onSelectListener) {
        ListDialog<T> dialog = new ListDialog<T>(context, content) {
            @Override
            public void onSelected(T object, int position) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(position, object);
                }
            }

            @Override
            public String getLabel(T object) {
                return String.valueOf(object);
            }
        };
        dialog.setTitle(title);
        if (!dialog.isShowing()){
            dialog.show();
        }
        return dialog;

    }
}
