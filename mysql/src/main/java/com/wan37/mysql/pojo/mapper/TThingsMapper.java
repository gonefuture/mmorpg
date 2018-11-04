package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TThings;
import com.wan37.mysql.pojo.entity.TThingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TThingsMapper {
    int countByExample(TThingsExample example);

    int deleteByExample(TThingsExample example);

    int deleteByPrimaryKey(String itemId);

    int insert(TThings record);

    int insertSelective(TThings record);

    List<TThings> selectByExample(TThingsExample example);

    TThings selectByPrimaryKey(String itemId);

    int updateByExampleSelective(@Param("record") TThings record, @Param("example") TThingsExample example);

    int updateByExample(@Param("record") TThings record, @Param("example") TThingsExample example);

    int updateByPrimaryKeySelective(TThings record);

    int updateByPrimaryKey(TThings record);
}