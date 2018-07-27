package com.firstapp.widget.pop;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.firstapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:40
 */
public class PopupWin {
    private View mParentView;
    private View mContentView;
    private PopupWindow popupWindow;
    private int mWidth, mHeight;
    private int mAnimstyle;
    private boolean mFocusable;

    private OnShowStateChangeCallBack mOnShowStateChangeCallBack;

    private PopupWin(View view1, View view2) {
        this.mParentView = view1;
        this.mContentView = view2;
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public static class Builder {
        private Context context;
        private LayoutInflater layoutInflater;
        private View parentView;
        private View contentView;
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        private int height = WindowManager.LayoutParams.WRAP_CONTENT;
        private int animStyle = R.style.PopupBottomAnim;
        private boolean focusable = true;

        private Builder(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public static Builder create(Context context) {
            return new Builder(context);
        }

        public Builder setParentView(View view) {
            this.parentView = view;
            return this;
        }

        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        public Builder setContentViewLayout(int layoutId) {
            this.contentView = layoutInflater.inflate(layoutId, null);
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setAnimStyle(int animStyle) {
            this.animStyle = animStyle;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public PopupWin build() {
            if (null == parentView) {
                throw new RuntimeException("you should set setParentView before build");
            }

            if (null == this.contentView) {
                throw new RuntimeException("you should set setContentView or setData before build");
            }

            final PopupWin win = new PopupWin(parentView, contentView);
            win.mWidth = width;
            win.mHeight = height;
            win.mAnimstyle = animStyle;
            win.mFocusable = focusable;

            win.build();
            return win;
        }

        class PopItem {
            private String key;
            private String val;

            PopItem(String k, String v) {
                this.key = k;
                this.val = v;
            }

            public String getKey() {
                return key;
            }

            public String getVal() {
                return val;
            }
        }

        private List<PopItem> convertToList(Map<String, String> map) {
            List<PopItem> list1 = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list1.add(new PopItem(entry.getKey(), entry.getValue()));
            }
            return list1;
        }
    }


    public void build() {
        popupWindow = new PopupWindow(mContentView, mWidth, mHeight);

        popupWindow.setFocusable(mFocusable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(10);
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        if (0 != mAnimstyle) {
            popupWindow.setAnimationStyle(mAnimstyle);
        }
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (null != mParentView) {
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ObjectAnimator.ofFloat(mParentView, "alpha", 0.5f, 1.0f).setDuration(500).start();
                    if(null != mOnShowStateChangeCallBack){
                        mOnShowStateChangeCallBack.onDismiss();
                    }
                }
            });
        }
    }

    public void show() {
        show(Gravity.BOTTOM, 0, 0);
    }

    public void show(int gravity, int x, int y) {
        show(mParentView, gravity, x, y);
    }

    public void show(View parent, int gravity, int x, int y) {
        check();

        popupWindow.showAtLocation(parent, gravity, x, y);
        if (null != mParentView) {
            ObjectAnimator.ofFloat(mParentView, "alpha", 1.0f, 0.5f).setDuration(500).start();
        }
        if (null != mOnShowStateChangeCallBack) {
            mOnShowStateChangeCallBack.onShow();
        }
    }

    public PopupWin setAction(@IdRes int viewId, View.OnClickListener clickListener) {
        check();
        View view = findViewById(viewId);
        if (null != view) {
            view.setOnClickListener(clickListener);
        }
        return this;
    }

    public View findViewById(@IdRes int viewId) {
        check();
        return mContentView.findViewById(viewId);
    }

    public TextView findTextViewById(@IdRes int viewId) {
        check();
        return (TextView) mContentView.findViewById(viewId);
    }

    public Button findButtonById(@IdRes int viewId) {
        check();
        return (Button) mContentView.findViewById(viewId);
    }

    private void check() {
        if (null == mContentView) {
            throw new RuntimeException("you should call build first");
        }
    }

    public boolean isShowing() {
        return popupWindow.isShowing();
    }

    public void dismiss() {
        check();
        popupWindow.dismiss();
        if (null != mOnShowStateChangeCallBack) {
            mOnShowStateChangeCallBack.onDismiss();
        }
    }


    public void setOnShowStateChangeCallBack(OnShowStateChangeCallBack callback) {
        mOnShowStateChangeCallBack = callback;
    }

    public interface OnShowStateChangeCallBack {
        void onShow();

        void onDismiss();
    }

}