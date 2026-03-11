package com.knowbird.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * ScreenUtils
 *
 */
public class ScreenUtils {

    private static final String TAG = "ScreenUtils";

    /**
     * dp 转 px
     * @param context context
     * @param dpValue dpValue
     * @return px值
     */
    public static int dpToPx(Context context, float dpValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (dpValue * metrics.density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context
     * @param pxValue
     * @return dp值
     */
    public static float pxToDp(Context context, float pxValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return pxValue / metrics.density;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            Log.e(TAG, "getStatusBarHeight: context is null");
        }
        int statusBarHeight = 0;
        assert context != null;
        int resourceId = context.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG, "getStatusBarHeight: " + statusBarHeight);
        return statusBarHeight;
    }

    /**
     * 获取导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (context == null) {
            Log.e(TAG, "getStatusBarHeight: context is null");
        }
        int navigationBarHeight = 0;
        assert context != null;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG, "getStatusBarHeight: " + navigationBarHeight);
        return navigationBarHeight;
    }
}
