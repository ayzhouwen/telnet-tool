package com.jcca.common.util;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyIdUtil {
    public static String getIncId(){
        return IdUtil.getSnowflake(1,1).nextIdStr();
    }

    public static void main(String[] args) {
        for (int i = 0; i <10000 ; i++) {
            log.info(getIncId());
        }
    }
}
