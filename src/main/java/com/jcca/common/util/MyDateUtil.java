package com.jcca.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 时间工具
 */
public class MyDateUtil {
    /**
     * ldt时间获取毫秒数
     * @param ldt
     * @return
     */
    public static String ltdToMillisecond(LocalDateTime ldt) {
        //获取毫秒数
        Long milliSecond = ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return milliSecond.toString();
    }


    /**
     * 输出执行时间
     * @param name
     * @param starttime
     */
    public static String execTime(String name,Long starttime){
        Double time= Convert.toDouble(DateUtil.spendMs(starttime));
        StringBuilder sb=new StringBuilder();
        sb.append(name).append(":").append(time).append(",指定时间毫秒,").append( NumberUtil.decimalFormat("#.##",time/1000))
                .append("秒,").append(NumberUtil.decimalFormat("#.##",time/1000/60)).append("分钟");
        return sb.toString();
    }

}
