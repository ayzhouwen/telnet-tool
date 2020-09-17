package com.jcca.common.util;

/**
 * 日志工具
 * 
 * @author Manager
 *
 */
public class AppLogUtils {

    public static String logStr(String prefix, String suffix, String data) {
        return "[" + prefix + " | (*^_^*) | " + suffix + "]:" + data;
    }

}
