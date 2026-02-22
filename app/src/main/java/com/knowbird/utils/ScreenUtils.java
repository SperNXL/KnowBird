package com.knowbird.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenUtils {

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
}
