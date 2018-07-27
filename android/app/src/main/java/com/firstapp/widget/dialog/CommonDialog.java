package com.firstapp.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firstapp.R;
import com.firstapp.base.BaseDialog;


/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:03
 */

class CommonDialog extends BaseDialog {

    private TextView titleTv;
    private TextView contentTv;
    private Button leftBtn, rightBtn, centerBtn;
    private LinearLayout buttonsLayout;
    private View titleLinear;
    private View leftDivider;
    private View rightDivider;
    private View topDivider;
    private View close;

    protected CommonDialog(Context context) {
        super(context);
    }

    CommonDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CommonDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public int getRootLayoutId() {
        return R.layout.dialog_common;
    }

    @Override
    public void bindView() {

        titleTv = (TextView) findViewById(R.id.dialog_title);
        contentTv = (TextView) findViewById(R.id.dialog_content);
        leftBtn = (Button) findViewById(R.id.dialog_btn_left);
        rightBtn = (Button) findViewById(R.id.dialog_btn_right);
        centerBtn = (Button) findViewById(R.id.dialog_btn);
        close = findViewById(R.id.dialog_close);
        titleLinear = findViewById(R.id.dialog_title_linear);
        buttonsLayout = (LinearLayout) findViewById(R.id.dialog_btns);

        leftDivider = findViewById(R.id.left_divider);
        rightDivider = findViewById(R.id.right_divider);
        topDivider = findViewById(R.id.top_divider);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public CommonDialog setButtons(final Btn... buttons) {

        if (buttons != null) {
            switch (buttons.length) {
                case 1:
                    buttonsLayout.removeView(leftBtn);
                    buttonsLayout.removeView(rightBtn);
                    leftDivider.setVisibility(View.GONE);
                    rightDivider.setVisibility(View.GONE);
                    final Btn center = buttons[0];
                    centerBtn.setText(center.getText());
                    centerBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dismiss();
                            if (center.getListener() != null) {
                                center.getListener().onClick(v);
                            }
                        }
                    });
                    break;

                case 2:
                    buttonsLayout.removeView(centerBtn);
                    rightDivider.setVisibility(View.GONE);
                    leftDivider.setVisibility(View.VISIBLE);
                    final Btn left = buttons[0];
                    final Btn right = buttons[1];
                    leftBtn.setText(left.getText());
                    leftBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dismiss();
                            if (left.getListener() != null) {
                                left.getListener().onClick(v);
                            }
                        }
                    });

                    rightBtn.setText(right.getText());
                    rightBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dismiss();
                            if (right.getListener() != null) {
                                right.getListener().onClick(v);
                            }
                        }
                    });
                    break;

                default:
                    buttonsLayout.removeAllViews();
                    contentTv.setBackgroundResource(R.drawable.dialog_bottom);
                    topDivider.setVisibility(View.GONE);
                    close.setVisibility(View.VISIBLE);
                    break;
            }
        }else {
            buttonsLayout.removeAllViews();
            contentTv.setBackgroundResource(R.drawable.dialog_bottom);
            topDivider.setVisibility(View.GONE);
            close.setVisibility(View.VISIBLE);
        }

        return this;
    }

    public CommonDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        } else {
            titleLinear.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonDialog setTitleGravity(int gravity) {
        if (titleTv != null) {
            titleTv.setGravity(gravity);
        }
        return this;
    }

    public CommonDialog setContent(CharSequence content) {
        if (!TextUtils.isEmpty(content)) {
            contentTv.setText(content + "");
        } else {
            contentTv.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonDialog setGravity(int gravity) {
        if (contentTv != null) {
            contentTv.setGravity(gravity);
        }
        return this;
    }

    @Override
    public void afterBindView() {

    }
}
