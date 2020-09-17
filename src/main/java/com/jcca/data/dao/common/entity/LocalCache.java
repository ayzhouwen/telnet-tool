package com.jcca.data.dao.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen_wj
 * @since 2020-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("LOCAL_CACHE")
public class LocalCache implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("CACHE_KEY")
    private String cacheKey;

    @TableField("CACHE_VALUE")
    private String cacheValue;

    @TableField("CREATE_TIME")
    private String createTime;

    @TableField("UPDATE_TIME")
    private String updateTime;

    @TableField("EXPIRE_TIME")
    private String expireTime;


}
