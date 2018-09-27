package com.wan37.mysql.dao;

import com.wan37.mysql.pojo.entity.GameRole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 10:56
 * @version 1.00
 * Description: mmorpg
 */

public interface PlayerLoginDao {

    @Select("SELECT * FROM game_role  WHERE id IN  (SELECT game_role_id FROM player_roles WHERE player_id = 13533805040)")
    List<GameRole> findGameRolesById();
}
