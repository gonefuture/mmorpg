package com.wan37.gameServer.game.RoleProperty.service;

import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.gameRole.model.Player;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:08
 * @version 1.00
 * Description: mmorpg
 */



@Service
public class RolePropertyService {



    @Resource
    private RolePropertyManager rolePropertyManager;

    /**
     *  加载角色属性
     * @param player 角色属性
     */
    public void loadRoleProperty(Player player) {
        List<RoleProperty> rolePropertyList = player.getRolePropertyList();
        for (int key=1; key <=8; key++ ) {
            RoleProperty roleProperty = rolePropertyManager.get(key);
            rolePropertyList.add(roleProperty);
        }

    }
}
