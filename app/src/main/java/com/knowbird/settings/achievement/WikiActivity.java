package com.knowbird.settings.achievement;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.knowbird.BaseActivity;
import com.knowbird.R;
import com.knowbird.utils.ScreenUtils;

/**
 * 鸟类百科
 *
 */
public class WikiActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wiki);
        mContext = getApplicationContext();

        initView();
    }

    private void initView() {
        View rootView = findViewById(android.R.id.content);
        TextView cnName = findViewById(R.id.cn_name);
        Intent intent = getIntent();
        String mName = intent.getStringExtra("m_name");
        cnName.setText(mName);

        // 添加状态栏&导航栏高度的padding
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtils.getStatusBarHeight(mContext);
            int navigationBarHeight = ScreenUtils.getNavigationBarHeight(mContext);
            rootView.setPadding(0, statusBarHeight, 0, navigationBarHeight);

        }
    }
}