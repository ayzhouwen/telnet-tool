package com.jcca.common.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Liu Jia Qi
 * @Date: 2020/1/6
 * @Describe: 操作消息
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -5062046292152025468L;

    public static final Integer COLLECT_ERRO_CODE = -1;
    
    private Integer code;

    private String msg;

    private T data;

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultVo success() {
        return new ResultVo(0, "操作成功", null);
    }

    public static ResultVo success(Object data) {
        return new ResultVo(0, "操作成功", data);
    }


    public static ResultVo error() {
        return new ResultVo(404, "操作失败", null);
    }

    /**
     * 采集错误
     * @param message
     * @return
     */
    public static ResultVo<String> collectError(String message) {
    	return new ResultVo<String>(COLLECT_ERRO_CODE, message, null);
    }
    /**
     * 采集成功
     * @param message
     * @return
     */
    public static ResultVo<String> collectSuccess(String message) {
    	return new ResultVo<String>(0, message, null);
    }
}
