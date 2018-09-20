package com.wan37.mysql.pojo.mapper;

import com.wan37.mysql.pojo.entity.Player;
import com.wan37.mysql.pojo.entity.PlayerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerMapper {
    int countByExample(PlayerExample example);

    int deleteByExample(PlayerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Player record);

    int insertSelective(Player record);

    List<Player> selectByExample(PlayerExample example);

    Player selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Player record, @Param("example") PlayerExample example);

    int updateByExample(@Param("record") Player record, @Param("example") PlayerExample example);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}