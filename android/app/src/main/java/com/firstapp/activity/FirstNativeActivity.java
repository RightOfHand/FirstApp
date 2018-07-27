package com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.react.ReactActivity;
import com.firstapp.R;
import com.firstapp.constant.ExtraKeys;

/**
 * Description:
 * Created by song on 2018/7/23.
 * email：bjay20080613@qq.com
 */
public class FirstNativeActivity extends ReactActivity {
    private TextView tvText;
    private TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_native);
        String param=getIntent().getStringExtra(ExtraKeys.EXTRA_KEY);

        tvText=(TextView) findViewById(R.id.tv_Text);
        tvBack=(TextView) findViewById(R.id.tv_back);

        tvText.setText(param);
        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstNativeActivity.this,SecondActivity.class));
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
