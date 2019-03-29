package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TQuestProgress;
import com.wan37.mysql.pojo.entity.TQuestProgressExample;
import com.wan37.mysql.pojo.entity.TQuestProgressKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TQuestProgressMapper {
    int countByExample(TQuestProgressExample example);

    int deleteByExample(TQuestProgressExample example);

    int deleteByPrimaryKey(TQuestProgressKey key);

    int insert(TQuestProgress record);

    int insertSelective(TQuestProgress record);

    List<TQuestProgress> selectByExample(TQuestProgressExample example);

    TQuestProgress selectByPrimaryKey(TQuestProgressKey key);

    int updateByExampleSelective(@Param("record") TQuestProgress record, @Param("example") TQuestProgressExample example);

    int updateByExample(@Param("record") TQuestProgress record, @Param("example") TQuestProgressExample example);

    int updateByPrimaryKeySelective(TQuestProgress record);

    int updateByPrimaryKey(TQuestProgress record);
}