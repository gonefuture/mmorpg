package com.wan37.gameServer.game.gameRole.model;

import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.things.modle.ThingProperty;
import com.wan37.gameServer.game.things.modle.Things;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 11:57
 * @version 1.00
 * Description: 装备栏
 */
@Slf4j
public class EquipmentBar  {

    private Map<String,Things> map = new HashMap<>();

    public void add(Player player, Things things) {
        Map<Integer,RoleProperty> rolePropertiesMap = player.getRolePropertyMap();
        log.debug("rolePropertiesMap {}",rolePropertiesMap);
        if (rolePropertiesMap == null)
            return ;

        Set<RoleProperty> rolePropertySet = things.getThingRoleProperty();
        if (rolePropertySet == null)
            return;
        rolePropertySet.forEach(
                thingRoleProperty -> {
                    log.debug("thingRoleProperty {}", thingRoleProperty);
                    Integer rolePropertyId =  thingRoleProperty.getKey();
                    // 角色的属性
                    RoleProperty roleProperty = rolePropertiesMap.get(rolePropertyId);

                    Integer currentValue = roleProperty.
                            getCurrentValue();
                    currentValue += thingRoleProperty.getCurrentValue();
                    // 记录属性变化
                    roleProperty.setCurrentValue(currentValue);
                }
        );
        map.put(things.getPart(), things);
        log.debug(" map.put(things.getPart(), things); {}",  map);
    }

    public void remove(String part) {
        map.remove(part);
    }

    public Collection<Things> list() {
        return map.values();
    }



}
