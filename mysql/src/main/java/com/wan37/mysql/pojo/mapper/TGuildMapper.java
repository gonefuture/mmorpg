package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TGuild;
import com.wan37.mysql.pojo.entity.TGuildExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TGuildMapper {
    int countByExample(TGuildExample example);

    int deleteByExample(TGuildExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TGuild record);

    int insertSelective(TGuild record);

    List<TGuild> selectByExample(TGuildExample example);

    TGuild selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TGuild record, @Param("example") TGuildExample example);

    int updateByExample(@Param("record") TGuild record, @Param("example") TGuildExample example);

    int updateByPrimaryKeySelective(TGuild record);

    int updateByPrimaryKey(TGuild record);
}