package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TGameObjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TGameObjectMapper {
    int countByExample(TGameObjectExample example);

    int deleteByExample(TGameObjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TGameObject record);

    int insertSelective(TGameObject record);

    List<TGameObject> selectByExample(TGameObjectExample example);

    TGameObject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TGameObject record, @Param("example") TGameObjectExample example);

    int updateByExample(@Param("record") TGameObject record, @Param("example") TGameObjectExample example);

    int updateByPrimaryKeySelective(TGameObject record);

    int updateByPrimaryKey(TGameObject record);
}