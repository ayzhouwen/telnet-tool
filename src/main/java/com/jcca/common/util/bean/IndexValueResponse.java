package com.jcca.common.util.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.sql.Timestamp;

@Data
public class IndexValueResponse {

    /**
     * 采集时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp collectTime;

    /**
     * 指标值
     */
    private String value;

    private String name;



}
