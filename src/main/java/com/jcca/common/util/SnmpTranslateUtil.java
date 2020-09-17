package com.jcca.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SnmpTranslateUtil {
    public static String translateMib(String oid) {
        String result = null;
        try {
            result = ShellUtil.execShell("snmptranslate -Ln -Td  " + oid);
        } catch (Exception e) {
            log.error("解析mib异常：", e);
        }
        if (result != null) {
            String pattern = "DESCRIPTION[\\S\\s]+?\\\"([\\S\\s]+?)\\\"";
            Pattern r = Pattern.compile(pattern);
            Matcher matcher = r.matcher(result);
            if (matcher.find()) {
                return matcher.group(1);//.replace("\r\n"," ").replace("\n"," ");
            }
        }
        return null;
    }
}
