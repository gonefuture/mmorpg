package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.TSkill;
import com.wan37.mysql.pojo.entity.TSkillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSkillMapper {
    int countByExample(TSkillExample example);

    int deleteByExample(TSkillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSkill record);

    int insertSelective(TSkill record);

    List<TSkill> selectByExample(TSkillExample example);

    TSkill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSkill record, @Param("example") TSkillExample example);

    int updateByExample(@Param("record") TSkill record, @Param("example") TSkillExample example);

    int updateByPrimaryKeySelective(TSkill record);

    int updateByPrimaryKey(TSkill record);
}