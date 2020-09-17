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
 * @since 2020-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InterfaceMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String ip;

    private String interfaceName;

    private Long inBytes;

    private Long outBytes;

    private String collectDate;

    private Long intervalMs;


}
