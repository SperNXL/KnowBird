package com.knowbird;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.knowbird.utils.ToastUtils;

/**
 * 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract View getRootView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 强制设置为竖屏
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setupSystemBarInsets();
        // 初始化 Toast
        ToastUtils.init(getApplicationContext());
    }

    /**
     * 使用 WindowInsetsCompat 动态适配系统栏（状态栏 + 导航栏/手势条），
     * 避免静态获取导航栏高度导致的适配问题。
     */
    private void setupSystemBarInsets() {
        View rootView = getRootView();
        if (rootView == null) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets.inset(systemBars);
        });
    }
}
