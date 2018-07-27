package com.firstapp.base;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:02
 */
interface IDialog {

    void dismiss();

    interface OnCancelListener {
        void onCancel(IDialog dialog);
    }

    interface OnClickListener {
        void onClick(IDialog dialog, int which);
    }

}
