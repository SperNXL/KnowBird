package com.knowbird;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.knowbird.utils.ScreenUtils;

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
        ScreenUtils.setSystemBarHeight(getActivityContext(), getRootView());
    }

    private Context getActivityContext() {
        return this;
    }
}
