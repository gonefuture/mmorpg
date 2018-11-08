package com.wan37.gameServer.game.roleProperty.service;

import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.gameRole.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:08
 * @version 1.00
 * Description: mmorpg
 */



@Service
@Slf4j
public class RolePropertyService {



    @Resource
    private RolePropertyManager rolePropertyManager;

    /**
     *  加载角色属性
     * @param player 角色属性
     */
    public void loadRoleProperty(Player player) {
        Map<Integer,RoleProperty> rolePropertyMap = player.getRolePropertyMap();
        for (int key=1; key <=6; key++ ) {
            RoleProperty roleProperty = rolePropertyManager.get(key);
            rolePropertyMap.put(roleProperty.getKey(),roleProperty);
            log.debug("rolePropertyMap {}",rolePropertyMap);
        }

    }

    public RoleProperty getRoleProperty(Integer id) {
        return rolePropertyManager.get(id);
    }
}
