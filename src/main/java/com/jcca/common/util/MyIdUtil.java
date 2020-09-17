package com.jcca.common.util;


import cn.hutool.core.util.IdUtil;

public class MyIdUtil {
    public static String getIncId(){
        return IdUtil.getSnowflake(1,1).nextIdStr();
    }

    public static void main(String[] args) {
        for (int i = 0; i <10000 ; i++) {
            System.out.println(getIncId());
        }
    }
}
