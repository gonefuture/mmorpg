package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.map.Position;
import com.wan37.gameServer.entity.role.Adventurer;
import com.wan37.gameServer.entity.role.Role;
import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.mapper.GameRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 16:03
 * @version 1.00
 * Description: 角色登陆服务
 */
@Service
public class RoleLoginService {

    @Resource
    private GameRoleMapper gameRoleMapper;

    public GameRole login(Long id) {
        return gameRoleMapper.selectByPrimaryKey(id);
    }

}
