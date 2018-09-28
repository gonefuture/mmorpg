package com.wan37.gameServer.service;


import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.entity.GameRoleExample;
import com.wan37.mysql.pojo.entity.Player;


import com.wan37.mysql.pojo.mapper.GameRoleMapper;
import com.wan37.mysql.pojo.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 18:02
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Service
public class PlayerLoginService {

    @Resource
    private PlayerMapper playerMapper;

    @Resource
    private GameRoleMapper gameRoleMapper;




    public PlayerLoginService() {
        log.info("PlayerLoginService加入容器");
    }

    /*
       * 判断用户id和密码是否正确
     */
    public List<GameRole> login(Long plyerId,String password) {
        Player player = playerMapper.selectByPrimaryKey(plyerId);
        if (player == null) {
            return null;
        } else {
            if (player.getPassword().equals(password)) {
                // 通过game_role表的player_id 查找与之相关的角色
                GameRoleExample gameRoleExample = new GameRoleExample();
                gameRoleExample.createCriteria().andPlayerIdEqualTo(plyerId);
                List<GameRole> gameRoleList = gameRoleMapper.selectByExample(gameRoleExample);
                log.debug("查出的角色列表： "+ gameRoleList);
                return gameRoleList;
            } else {
                return null;
            }
        }
    }
}
