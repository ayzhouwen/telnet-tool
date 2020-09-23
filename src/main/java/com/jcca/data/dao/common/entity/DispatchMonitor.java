package com.jcca.data.dao.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen_wj
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DispatchMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     *采集周期id
     */
    private Long cycleId;

    /**
     * 车站分组id
     */
    private String stationGroupId;

    /**
     * 路由/交换机 ip
     */
    private String ip;

    /**
     * 监控端口名
     */
    private String interfaceName;

    /**
     * 第一次采集流入字节
     */
    private Long oneInBytes;

    /**
     * 第一次采集流出字节
     */
    private Long oneOutBytes;
    /**
     * 第二次采集流入字节
     */
    private Long twoInBytes;


    /**
     * 第二次采集流出字节
     */
    private Long  twoOutBytes;

    /**
     * 第一次和第二次采集流入字节差
     */
    private Long inBytesDiff;

    /**
     * 第一次和第二次采集流出字节差
     */
    private Long outBytesDiff;

    /**
     * 调度器分配任务数
     */
    private Long sysDispatch;

    /**
     * 调度器发送数
     */
    private Long sysSend;

    /**
     * 调度器接收数
     */
    private Long sysRevice;

    /**
     * 调度器收发错误数
     */
    private Long sysError;

    /**
     * 上次发送时间
     */
    private String sysLastSendTime;

    /**
     * 上次调度分配任务时间
     */
    private String sysLastDispatchTime;
    /**
     * 上次接收请求时间
     */
    private String sysLastReviceTime;

    /**
     * 上次收发错误时间
     */
    private String sysLastErrorTime;

    /**
     * 上次请求处理完成时间
     */
    private String sysLastHandleCompleteTime;

    /**
     * 上次处理错误时间
     */
    private String sysLastHandleErrorTime;

    /**
     * 调度完成数
     */
    private Long sysHandleComplete;

    /**
     * 处理错误数
     */
    private Long sysHandleError;

    /**
     * 调度器统计合计发送数
     */
    private Long sysSumSendByte;

    /**
     * 调度器统计收入字节数
     */
    private Long sysSumReceiveByte;

    /**
     * 开始采集时间
     */
    private String startTime;

    /**
     * 结束采集时间
     */
    private String endTime;

    /**
     * 开始时间与结束时间差
     */
    private Long intervalMs;

    /**
     * 调度器运行时间
     */
    private Long sysRuntimeMs;


}
