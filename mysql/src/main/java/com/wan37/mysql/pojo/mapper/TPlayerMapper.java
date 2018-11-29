package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TPlayer;
import com.wan37.mysql.pojo.entity.TPlayerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPlayerMapper {
    int countByExample(TPlayerExample example);

    int deleteByExample(TPlayerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TPlayer record);

    int insertSelective(TPlayer record);

    List<TPlayer> selectByExampleWithBLOBs(TPlayerExample example);

    List<TPlayer> selectByExample(TPlayerExample example);

    TPlayer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TPlayer record, @Param("example") TPlayerExample example);

    int updateByExampleWithBLOBs(@Param("record") TPlayer record, @Param("example") TPlayerExample example);

    int updateByExample(@Param("record") TPlayer record, @Param("example") TPlayerExample example);

    int updateByPrimaryKeySelective(TPlayer record);

    int updateByPrimaryKeyWithBLOBs(TPlayer record);

    int updateByPrimaryKey(TPlayer record);
}