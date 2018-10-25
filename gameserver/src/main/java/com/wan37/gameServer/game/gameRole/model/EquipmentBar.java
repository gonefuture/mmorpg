package com.wan37.gameServer.game.gameRole.model;

import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.things.modle.ThingProperty;
import com.wan37.gameServer.game.things.modle.Things;

import java.util.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 11:57
 * @version 1.00
 * Description: 装备栏
 */
public class EquipmentBar  {

    private Map<String,Things> map = new HashMap<>();

    public void add(Player player, Things things) {
        Map<Integer,RoleProperty> rolePropertiesMap = player.getRolePropertyMap();
        if (rolePropertiesMap == null)
            return ;

        Set<RoleProperty> rolePropertySet = things.getThingRoleProperty();
        if (rolePropertySet == null)
            return;
        rolePropertySet.forEach(
                thingRoleProperty -> {
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
    }

    public void remove(String part) {
        map.remove(part);
    }

    public Collection<Things> list() {
        return map.values();
    }



}
