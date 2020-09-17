package com.jcca.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyNumUtil {
    /**
     * String里存放的是double 转 long ,解决hutool的string中是科学计数法转long出错问题
     * @param v
     * @return
     */
   public static Long SD2L(String v){
       if (StrUtil.isNotEmpty(v)&&!v.equals("null")) {
           Long result = Convert.toDouble(v).longValue();
           return result;
       } else {
           return 0L;
       }
   }

    /**
     * 格式化2位小数
     * @param v
     * @return
     */
   public static String formatDS(String v){
       if (StrUtil.isEmpty(v)||v.toLowerCase().equals("null")){
           return "0";
       }else {
           return NumberUtil.roundStr(v,3);
       }
   }

    public static void main(String[] args) {
        String a="2.450300172E9";
        log.info(SD2L(a)+"");
    }
}
