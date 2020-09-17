package com.jcca.data.dao.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommonMapper {


    List<Map<String, Object>> runSelectSql(@Param("sql") String sql);

}
