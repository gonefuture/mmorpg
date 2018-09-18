package cn.zhku.mysql.pojo.entity;

import cn.zhku.mysql.pojo.entity.PlayerRoles;
import cn.zhku.mysql.pojo.entity.PlayerRolesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayerRolesMapper {
    int countByExample(PlayerRolesExample example);

    int deleteByExample(PlayerRolesExample example);

    int insert(PlayerRoles record);

    int insertSelective(PlayerRoles record);

    List<PlayerRoles> selectByExample(PlayerRolesExample example);

    int updateByExampleSelective(@Param("record") PlayerRoles record, @Param("example") PlayerRolesExample example);

    int updateByExample(@Param("record") PlayerRoles record, @Param("example") PlayerRolesExample example);
}