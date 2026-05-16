package com.knowbird.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils {
    private static final String TAG = "StringUtils";

    /**
     * 字符串转 List
     * @param str
     * @return
     */
    public static List<String> string2List(String str) {
        if (str == null) {
            Log.e(TAG, "str is null");
        }
        String substring = str.substring(1, str.length() - 1);
        String[] split = substring.split(", ");
        return Arrays.asList(split);
    }
}
