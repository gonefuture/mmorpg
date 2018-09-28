package com.wan37.gameServer.service;

import com.wan37.gameServer.manager.cache.RoleCacheManager;
import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.mapper.GameRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/12 16:03
 * @version 1.00
 * Description: 角色登陆服务
 */
@Service
public class RoleLoginService {

    @Resource
    private RoleCacheManager roleCacheManager;

    @Resource
    private GameRoleMapper gameRoleMapper;


    /**
     *  角色登陆
     */
    public GameRole login(Long roleId) {
        GameRole gameRoleCache  = roleCacheManager.get(roleId);

        if (gameRoleCache == null) {
            GameRole gameRole =  gameRoleMapper.selectByPrimaryKey(roleId);
            roleCacheManager.put(roleId,gameRole);
            return gameRole;
        } else {
            return gameRoleCache;
        }
    }

}
