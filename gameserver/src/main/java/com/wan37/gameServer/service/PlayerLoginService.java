package com.wan37.gameServer.service;

import com.wan37.mysql.dao.PlayerLoginDao;
import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.entity.Player;
import com.wan37.mysql.pojo.entity.PlayerRoles;
import com.wan37.mysql.pojo.mapper.GameRoleMapper;
import com.wan37.mysql.pojo.mapper.PlayerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
    private PlayerLoginDao playerLoginDao;

    public PlayerLoginService() {
        log.info("PlayerLoginService加入容器");
    }

    /*
       * 判断用户id和密码是否正确
     */
    public List<GameRole> login(Long id,String password) {
        Player player = playerMapper.selectByPrimaryKey(id);
        if (player == null) {
            return null;
        } else {
            if (player.getPassword().equals(password)) {
                List<GameRole> gameRoleList = playerLoginDao.findGameRolesById();
                log.debug("查出的角色列表： "+ gameRoleList);
                return gameRoleList;
            } else {
                return null;
            }
        }
    }
}
