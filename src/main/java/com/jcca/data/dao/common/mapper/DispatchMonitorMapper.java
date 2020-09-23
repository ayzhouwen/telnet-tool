package com.jcca.data.dao.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jcca.data.dao.common.entity.DispatchMonitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chen_wj
 * @since 2020-09-21
 */
public interface DispatchMonitorMapper extends BaseMapper<DispatchMonitor> {
    DispatchMonitor getInfo(@Param("stationGroupId") String stationGroupId,@Param("cycleId") Long cycleId);
}
