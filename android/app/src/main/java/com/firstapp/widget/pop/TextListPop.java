package com.firstapp.widget.pop;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firstapp.R;

import java.util.List;

import static com.firstapp.helper.AppHelper.getDimen;


/**
 *Description:
 *creator: song
 *Date: 2018/7/5 下午4:42
 */
public class TextListPop implements View.OnClickListener {
    private PopupWin mPowWindow;
    private Activity mContext;
    private List<String> list;
    private CallBackTag callBackTag;

    public TextListPop(Activity context, List<String> list, CallBackTag callBackTag) {
        this.mContext = context;
        this.list = list;
        this.callBackTag = callBackTag;
    }

    private void init() {
        if (mContext==null) return;
        if (mContext.getWindow()==null) return;
        View parentView = mContext.getWindow().getDecorView();
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_text_list, null,false);
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.ll_text_container);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, getDimen(R.dimen.btn_height_big)
        );
        for (int i = 0; i < list.size(); i++) {
            TextView tvItem = new TextView(mContext);
            tvItem.setText(list.get(i));
            tvItem.setTag(i);
            tvItem.setOnClickListener(this);
            tvItem.setLayoutParams(params);
            tvItem.setGravity(Gravity.CENTER);
            tvItem.setTextColor(0xff333333);
            tvItem.setTextSize(17);
            tvItem.setBackgroundResource(R.drawable.list_item_selector);
            layout.addView(tvItem);
        }

        mPowWindow = PopupWin.Builder.create(mContext)
                .setParentView(parentView)
                .setContentView(contentView)
                .build();
        mPowWindow.setAction(R.id.tv_pop_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPowWindow.dismiss();
            }
        });

    }

    public void show() {
        if (mPowWindow == null) {
            init();
        }
       if (mPowWindow!=null && !mPowWindow.isShowing()){
            mPowWindow.show();
        }
    }

    public boolean isShowing(){
        return mPowWindow!=null && mPowWindow.isShowing();
    }
    public void dismiss(){
        if (mPowWindow!=null) mPowWindow.dismiss();
    }
    @Override
    public void onClick(View v) {
        callBackTag.sendTag(((int) v.getTag()));
        mPowWindow.dismiss();
    }

    public interface CallBackTag {
        void sendTag(int i);
    }

}
