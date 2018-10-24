package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TThings;
import com.wan37.mysql.pojo.entity.TThingsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TThingsMapper {
    int countByExample(TThingsExample example);

    int deleteByExample(TThingsExample example);

    int insert(TThings record);

    int insertSelective(TThings record);

    List<TThings> selectByExample(TThingsExample example);

    int updateByExampleSelective(@Param("record") TThings record, @Param("example") TThingsExample example);

    int updateByExample(@Param("record") TThings record, @Param("example") TThingsExample example);
}