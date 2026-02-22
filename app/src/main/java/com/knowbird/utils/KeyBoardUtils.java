package com.knowbird.utils;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtils {
    private static final String TAG = "KeyBoardUtils";

    private static final int TOUCH_EXTEND_DP = 100;

    private static int touchExtendPx = 0;

    /**
     * 收起软键盘
     * @param context context
     * @param view view
     */
    public static void hideSoftKeyboard(Context context, View view) {
        if (context == null || view == null) return;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 是否需要隐藏输入法
     * @param mContext context
     * @param v v
     * @param event event
     * @return
     */
    public static boolean isShouldHideKeyboard(Context mContext, View v, MotionEvent event) {
        if (mContext == null) {
            return true;
        }
        if (touchExtendPx == 0) {
            dp2Px(mContext);
        }
        if (v != null && (v instanceof EditText)) {
            // 获取输入框的坐标范围
            int[] l = new int[2];
            v.getLocationOnScreen(l);
            int left = l[0] + touchExtendPx;
            int top = l[1] + touchExtendPx;
            int bottom = l[1] + v.getHeight() + touchExtendPx;
            int right = l[0] + v.getWidth() + touchExtendPx;
            // 判断点击位置是否在输入框外
            float touchX = event.getRawX();
            float touchY = event.getRawY();
            return !(touchX > left && touchX < right && touchY > top && touchY < bottom);
        }
        return true;
    }

    private static void dp2Px(Context mContext) {
        touchExtendPx = ScreenUtils.dpToPx(mContext, TOUCH_EXTEND_DP);
    }
}
