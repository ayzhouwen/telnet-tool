package com.jcca.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;


/**
 * 实例工具
 * 
 * @author Manager
 *
 */
public class AppBeanUtils {

    public static <T> T copy(Object srcObj, Class<T> destClass) {
        T destObj = null;
        if (srcObj == null) {
            return destObj;
        }

        try {
            destObj = destClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(srcObj, destObj);
        return destObj;
    }

    public static <T> List<T> copyList(List<?> srcObjs, Class<T> destClass) {
        if (srcObjs == null) {
            return new ArrayList<T>();
        }
        return srcObjs.stream().map(srcObj -> copy(srcObj, destClass)).collect(Collectors.toList());
    }
    
}
