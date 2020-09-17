package com.jcca.data.dao.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommonMapper {

    /**
     * 获取清除表的清除数据称最大Id
     * @param table 要清除的表名称
     * @param timeStr  '2020-05-23 15:55:06'
     * @param pageSize 单次清除数据大小
     * @param timeField 时间字段名称
     * @param idField id字段
     * @return
     */
    Integer getMaxIdByClearTime(@Param("table") String table,@Param("time") String timeStr,@Param("pageSize") Integer pageSize,@Param("timeField")String timeField,@Param("idField")String idField);

    /**
     * 根据最大id删除表中数据
     * @param table
     * @param maxId
     * @return
     */
    Integer deleteByMaxId(@Param("table") String table,@Param("maxId") Integer maxId);

    List<Map<String, Object>> runSelectSql(@Param("sql") String sql);

    /**
     * 执行单条增删改语句
     * @param sql
     */
    void runIUDSSql(@Param("sql") String sql);

}
