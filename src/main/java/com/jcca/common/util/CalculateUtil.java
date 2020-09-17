package com.jcca.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import com.jcca.common.util.bean.IndexValueResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class CalculateUtil {
    private static Logger log = LoggerFactory.getLogger(CalculateUtil.class);






    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    //秒转化成天、小时、秒
    public static String formatSeconds(long seconds) {
        String timeStr = seconds + "秒";
        if (seconds > 60) {
            long second = seconds % 60;
            long min = seconds / 60;
            timeStr = min + "分" + second + "秒";
            if (min > 60) {
                min = (seconds / 60) % 60;
                long hour = (seconds / 60) / 60;
                timeStr = hour + "小时" + min + "分" + second + "秒";
                if (hour > 24) {
                    hour = ((seconds / 60) / 60) % 24;
                    long day = (((seconds / 60) / 60) / 24);
                    timeStr = day + "天" + hour + "小时" + min + "分" + second + "秒";
                }
            }
        }
        return timeStr;
    }

    /**
     * 端口利用率
     * @return
     */

    public static double ifUtilization(long bytesIn,long bytesOut,long portSpeed){//录入量，流出量，端口速率
        double ifUtilization;
        if(portSpeed!=0){
            ifUtilization=(bytesIn+bytesOut)/(300*portSpeed);
        }else{
            ifUtilization=0.0;
        }
       return formatDouble(ifUtilization);
    }
    /**
     * IfHDplxUtilization利用率
     * @return
     */
    public static double ifHDplxUtilization(long bytesIn,long bytesOut,long portSpeed){//录入量，流出量，端口速率
        double ifHDplxUtilization;
        if(portSpeed!=0){
            ifHDplxUtilization=(bytesIn+bytesOut) * 100/(300 * 2 *portSpeed);
        }else{
            ifHDplxUtilization=0.0;
        }
        return formatDouble(ifHDplxUtilization);
    }

    /**
     * ifInFDplxUtilization端口流入率
     * @return
     */
    public static double ifInFDplxUtilization(long bytesIn,long portSpeed){//录入量，端口速率
        double ifInFDplxUtilization;
        if(portSpeed!=0){
            ifInFDplxUtilization=(bytesIn) * 100/(300 *portSpeed);
        }else{
            ifInFDplxUtilization=0.0;
        }
        return formatDouble(ifInFDplxUtilization);
    }

    /**
     * ifOutFDplxUtilization端口流出率
     * @return
     */
    public static double ifOutFDplxUtilization(long bytesOut,long portSpeed){//流出量，端口速率
        double ifOutFDplxUtilization;
        if(portSpeed!=0){
            ifOutFDplxUtilization=(bytesOut) * 100/(300 *portSpeed);
        }else{
            ifOutFDplxUtilization=0.0;
        }
        return formatDouble(ifOutFDplxUtilization);
    }

    /**
     * ifErrorPct端口误码率
     * @return
     */
    public static double ifErrorPct(long ifInErrors,long ifOutErrors,long ifInNUcastPkts,long ifOutNUcastPkts,long ifOutUcastPkts,long ifInUcastPkts){//流出量，端口速率
        double ifErrorPct;
        if(ifInNUcastPkts+ifOutNUcastPkts+ifOutUcastPkts+ifInUcastPkts==0){
            ifErrorPct=0;
        }else{
            ifErrorPct=  (ifInErrors+ifOutErrors)*100/(ifInNUcastPkts+ifOutNUcastPkts+ifOutUcastPkts+ifInUcastPkts);
        }
        return formatDouble(ifErrorPct);
    }
    /**
     * ifErrorPct端口流入误码率
     * @return
     */
    public static double ifInErrorPct(long ifInErrors,long ifInNUcastPkts,long ifInUcastPkts){
        double ifInErrorPct;
        if(ifInNUcastPkts+ifInUcastPkts==0){
            ifInErrorPct=0;
        }else{
            ifInErrorPct=  (ifInErrors)*100/(ifInNUcastPkts+ifInUcastPkts);
        }
        return formatDouble(ifInErrorPct);
    }

    /**
     * ifErrorPct端口流出误码率
     * @return
     */
    public static double ifOutErrorPct(long ifOutErrors,long ifOutNUcastPkts,long ifOutUcastPkts){
        double ifOutErrorPct;
        if(ifOutNUcastPkts+ifOutUcastPkts==0){
            ifOutErrorPct=0;
        }else{
            ifOutErrorPct=  (ifOutErrors)*100/(ifOutNUcastPkts+ifOutUcastPkts);
        }

        return formatDouble(ifOutErrorPct);
    }

    /**
     * ifDiscardPct端口丢包率
     * @return
     */
    public static double ifDiscardPct(long ifInDiscards,long ifOutDiscards,long ifInNUcastPkts,long ifInUcastPkts,long ifOutNUcastPkts,long ifOutUcastPkts){
        double ifDiscardPct;
        if(ifInNUcastPkts+ifInUcastPkts+ifOutNUcastPkts+ifOutUcastPkts==0){
            ifDiscardPct=0;
        }else{
            ifDiscardPct=  (ifInDiscards+ifOutDiscards)*100/(ifInNUcastPkts+ifInUcastPkts+ifOutNUcastPkts+ifOutUcastPkts);

        }
        return formatDouble(ifDiscardPct);
    }

    /**
     * ifNUcastPkts非单播数
     * @return
     */
    public static double ifNUcastPkts(long ifInNUcastPkts,long ifOutNUcastPkts){
        double ifNUcastPkts;
        ifNUcastPkts= ifInNUcastPkts+ifOutNUcastPkts ;
        return formatDouble(ifNUcastPkts);
    }
    /**
     * ifUcastPkts单播数
     * @return
     */
    public static double ifUcastPkts(long ifInUcastPkts,long ifOutUcastPkts){
        double ifUcastPkts;
        ifUcastPkts= ifInUcastPkts+ifOutUcastPkts ;
        return formatDouble(ifUcastPkts);
    }

    /**
     * NUcastVsUcast单播率
     * @return
     */
    public static double NUcastVsUcast(long ifInNUcastPkts,long ifOutNUcastPkts,long ifInUcastPkts,long ifOutUcastPkts){
        double NUcastVsUcast;
        if(ifInUcastPkts+ifOutUcastPkts==0){
            NUcastVsUcast=0;
        }else{
            NUcastVsUcast= (ifInNUcastPkts+ifOutNUcastPkts)/(ifInUcastPkts+ifOutUcastPkts);

        }
        return formatDouble(NUcastVsUcast);
    }



    public static double formatDouble(double d) {
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);


        return bg.doubleValue();
    }
    public static String getFormatStr(String octetString){
        try{
            if(!octetString.contains(":")) {
                return octetString;
            }
            String[] temps = octetString.split(":");
            byte[] bs = new byte[temps.length];
            for(int i=0;i<temps.length;i++)
                bs[i] = (byte)Integer.parseInt(temps[i],16);

            String encodename=null;
            encodename=EncodeUtils.getEncode(new BufferedInputStream( new ByteArrayInputStream(bs)),true);
            if (StrUtil.isEmpty(encodename)){
                encodename="UTF-8";
            }
            return new String(bs,encodename);
        }catch(Exception e){
            return null;
        }
    }


    public static List<Map<String,Object>> getListMap(List<IndexValueResponse> indexValue){
        List<Map<String,Object>> list = new ArrayList<>();
        indexValue.stream().forEach(c -> {
            Map<String,Object> map = new HashMap<>();
            map.put("collectTime",DateTimeUtil.formatDateTimeFormatHHmm(c.getCollectTime()));
            map.put("value",c.getValue());
            list.add(map);
        });

        return list;

    }

    /**
     * 指标计算
     */
    public static List<Map<String,Object>> computeList(List<IndexValueResponse> list1, List<IndexValueResponse> list2){
        List<Map<String,Object>> proportionList = new ArrayList<>();
        int i = 0 ;
        list1.forEach(c -> {
            Map<String,Object> map = new HashMap<>();
            map.put("COLLECTTIME",DateTimeUtil.formatDateTime(c.getCollectTime()));
            map.put("VALUE",getProportion(c.getValue(),list2.get(i).getValue()));
            proportionList.add(map);
        });
        return proportionList;
    }

    /**
     * 比例计算
     */
    public static Double getProportion(String value2, String value1) {
        if (StringUtils.isEmpty(value1)) {
            return  0.0;
        }
        if (StringUtils.isEmpty(value2)) {
            return  0.0;
        }
        return new BigDecimal(Double.valueOf(StringUtils.isEmpty(value1) ? "0" : value1)/Double.valueOf(StringUtils.isEmpty(value2) ? "0" : value1)* 100).setScale(4, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 平均值计算
     */
    public static Double getAvgProportion( List<IndexValueResponse> list) {
        if (CollectionUtils.isEmpty(list)){
            return  0.0;
        }
        Double avg = 0.0;
        for (IndexValueResponse map : list) {
            avg += Double.valueOf(StringUtils.isEmpty(map.getValue()) ? "0.0" : map.getValue());
        }
        double value = avg / list.size();
        if (value>0) value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

}
