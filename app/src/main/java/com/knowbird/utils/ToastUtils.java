package com.knowbird.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.knowbird.R;

/**
 * 全局自定义 Toast 工具类
 */
public class ToastUtils {

    private static Toast sToast;
    private static View sToastView;
    private static TextView tvMessage;
    private static ImageView ivIcon;

    /**
     * 初始化
     */
    public static void init(Context context) {
        if (sToastView == null) {
            sToastView = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
            tvMessage = sToastView.findViewById(R.id.tv_message);
            ivIcon = sToastView.findViewById(R.id.iv_icon);
        }
        if (sToast == null) {
            sToast = new Toast(context);
            sToast.setView(sToastView);
        }
    }

    /**
     * 短时间显示 Toast
     */
    public static void showShort(CharSequence message) {
        show(message, false, 0, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * 长时间显示 Toast
     */
    public static void showLong(CharSequence message) {
        show(message, false, 0, Toast.LENGTH_LONG, Gravity.CENTER);
    }

    /**
     * 带图标的 Toast
     */
    public static void showWithIcon(CharSequence message, int iconRes) {
        if (sToast == null) return;
        ivIcon.setVisibility(View.VISIBLE);
        ivIcon.setImageResource(iconRes);
        show(message, true, 0, Toast.LENGTH_SHORT, Gravity.CENTER);
    }

    /**
     * Toast 显示到指定位置
     *
     * @param message
     * @param gravity
     */
    public static void showInPosition(CharSequence message, int gravity) {
        show(message, false, 0, Toast.LENGTH_SHORT, gravity);
    }

    /**
     * show
     */
    private static void show(CharSequence message, boolean hasIcon, int iconRes,
                             int duration, int gravity) {
        if (sToast == null) return;
        tvMessage.setText(message);
        ivIcon.setVisibility(View.GONE);
        if (hasIcon && iconRes != 0) {
            ivIcon.setImageResource(iconRes);
            ivIcon.setVisibility(View.VISIBLE);
        }
        sToast.setDuration(duration);
        sToast.setGravity(gravity, 0, 0);
        sToast.show();
    }

    /**
     * 取消当前 Toast
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}