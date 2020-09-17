package com.jcca.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ArrayUtils;

@Component
public class ProfileUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    // 获取当前环境参数  exp: dev,release,test
    public static String getActiveProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (!ArrayUtils.isEmpty(profiles)) {
            return profiles[0];
        }
        return "";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.context = applicationContext;
    }
}
