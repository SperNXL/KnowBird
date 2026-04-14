package com.knowbird.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 字符串转 List
     * @param str
     * @return
     */
    public static List<String> string2List(String str) {
        String substring = str.substring(1, str.length() - 1);
        String[] split = substring.split(", ");
        return Arrays.asList(split);
    }
}
