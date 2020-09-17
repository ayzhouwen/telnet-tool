package com.jcca.common.config.mybits;

import cn.hutool.core.convert.Convert;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: csk
 * @Date: 2020/1/4
 * @Describe: 分页封装
 */
public class PagePlugin {

    /**
     * 请求分页数据
     *
     * @param page 页号
     * @param size 每页条数
     * @return
     */
    public static IPage<Object> startPage(Object page, Object size) {
        return new Page<>(Convert.toInt(page, 1), Convert.toInt(size, 10));
    }

    /**
     * 请求分页数据
     *
     * @param <T>
     * @param page 页号
     * @param size 每页条数
     * @return
     */
    public static <T> IPage<T> startPageT(Object page, Object size, Class<T> t) {
        return new Page<T>(Convert.toInt(page, 1), Convert.toInt(size, 10));
    }

}
