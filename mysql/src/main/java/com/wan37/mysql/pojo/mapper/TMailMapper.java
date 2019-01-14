package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TMail;
import com.wan37.mysql.pojo.entity.TMailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMailMapper {
    int countByExample(TMailExample example);

    int deleteByExample(TMailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMail record);

    int insertSelective(TMail record);

    List<TMail> selectByExampleWithBLOBs(TMailExample example);

    List<TMail> selectByExample(TMailExample example);

    TMail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMail record, @Param("example") TMailExample example);

    int updateByExampleWithBLOBs(@Param("record") TMail record, @Param("example") TMailExample example);

    int updateByExample(@Param("record") TMail record, @Param("example") TMailExample example);

    int updateByPrimaryKeySelective(TMail record);

    int updateByPrimaryKeyWithBLOBs(TMail record);

    int updateByPrimaryKey(TMail record);
}