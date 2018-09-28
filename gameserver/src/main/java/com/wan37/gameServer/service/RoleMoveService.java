package com.wan37.gameServer.service;

import com.wan37.gameServer.manager.CacheManager;
import com.wan37.mysql.pojo.entity.GameRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 17:32
 * @version 1.00
 * Description: 角色移动服务
 */

@Slf4j
@Service
public class RoleMoveService  {

    public void moveScene(long roleId,int sceneId) {
        GameRole gameRole = CacheManager.getGameRole(roleId);
        gameRole.setScene(sceneId);
    }

}
