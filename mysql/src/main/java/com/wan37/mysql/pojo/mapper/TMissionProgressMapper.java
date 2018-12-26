package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TMissionProgress;
import com.wan37.mysql.pojo.entity.TMissionProgressExample;
import com.wan37.mysql.pojo.entity.TMissionProgressKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMissionProgressMapper {
    int countByExample(TMissionProgressExample example);

    int deleteByExample(TMissionProgressExample example);

    int deleteByPrimaryKey(TMissionProgressKey key);

    int insert(TMissionProgress record);

    int insertSelective(TMissionProgress record);

    List<TMissionProgress> selectByExample(TMissionProgressExample example);

    TMissionProgress selectByPrimaryKey(TMissionProgressKey key);

    int updateByExampleSelective(@Param("record") TMissionProgress record, @Param("example") TMissionProgressExample example);

    int updateByExample(@Param("record") TMissionProgress record, @Param("example") TMissionProgressExample example);

    int updateByPrimaryKeySelective(TMissionProgress record);

    int updateByPrimaryKey(TMissionProgress record);
}