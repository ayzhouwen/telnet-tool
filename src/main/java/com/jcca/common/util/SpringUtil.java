package com.jcca.common.util;

import org.springframework.context.ApplicationContext;

public class SpringUtil {

    private static ApplicationContext ac = null;

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static void setApplicationContext(ApplicationContext ac) {
        SpringUtil.ac = ac;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static <T> T getBean(Class<T> className) {
        return getApplicationContext().getBean(className);
    }
}
