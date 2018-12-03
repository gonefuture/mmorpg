package com.wan37.gameServer.game.roleProperty.service;

import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.model.Things;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

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




    public void loadThingPropertyToPlayer(Player player, Things things) {
        Set<RoleProperty> thingPropertySet = things.getThingRoleProperty();
        Map<Integer,RoleProperty> playerPropertyMap = player.getRolePropertyMap();

        thingPropertySet.forEach(
                thingProperty -> {
                    // 改变属性
                    RoleProperty playerProperty = playerPropertyMap.get(thingProperty.getKey());
                    playerProperty.setValue(playerProperty.getValue() + thingProperty.getThingPropertyValue());

                }
        );


    }






    /**
     *  加载角色属性
     * @param player 角色属性
     */
    public void loadRoleProperty(Player player) {
        Map<Integer,RoleProperty> rolePropertyMap = player.getRolePropertyMap();
        for (int key=1; key <=6; key++ ) {
            RoleProperty roleProperty = rolePropertyManager.get(key);
            // 每个玩家角色的属性都独立
            RoleProperty playerRoleProperty  = new RoleProperty();
            BeanUtils.copyProperties(roleProperty, playerRoleProperty);
            rolePropertyMap.put(roleProperty.getKey(),playerRoleProperty);
            //log.debug("rolePropertyMap {}",rolePropertyMap);
        }

    }

    public RoleProperty getRoleProperty(Integer id) {
        return rolePropertyManager.get(id);
    }
}
