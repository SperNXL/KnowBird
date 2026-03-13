package com.knowbird.settings.achievement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

import com.knowbird.BaseActivity;
import com.knowbird.R;

/**
 * 鸟类百科
 *
 */
public class WikiActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected View getRootView() {
        return getWindow().getDecorView().getRootView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wiki);
        mContext = getApplicationContext();

        initView();
    }

    private void initView() {
        TextView cnName = findViewById(R.id.cn_name);
        Intent intent = getIntent();
        String mName = intent.getStringExtra("m_name");
        cnName.setText(mName);
    }
}