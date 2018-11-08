package com.wan37.gameServer.game.gameRole.model;

import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.things.modle.Things;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 11:57
 * @version 1.00
 * Description: 装备栏
 */
@Slf4j
@Data
public class EquipmentBar  {

    private Map<String,Things> map = new HashMap<>();

    public void add(Player player, Things things) {
        Map<Integer,RoleProperty> rolePropertiesMap = player.getRolePropertyMap();
        log.debug("rolePropertiesMap {}",rolePropertiesMap);
        if (rolePropertiesMap == null)
            return ;

        Set<RoleProperty> rolePropertySet = things.getThingRoleProperty();
        log.debug("rolePropertySet {}",rolePropertySet);
        if (rolePropertySet == null)
            return;
        rolePropertySet.forEach(
            thingRoleProperty -> {
                log.debug("thingRoleProperty {}", thingRoleProperty);
                Integer rolePropertyId =  thingRoleProperty.getKey();
                // 该角色的属性
                RoleProperty roleProperty = rolePropertiesMap.get(rolePropertyId);

                if (roleProperty != null) {
                    Integer currentValue = roleProperty.
                            getCurrentValue();
                    if (currentValue == null)
                        currentValue = 0 ;

                    Integer passValue  =  thingRoleProperty.getCurrentValue();
                    if (passValue == null)
                        passValue = 0;
                    // 加上基础值
                    currentValue += roleProperty.getValue();
                    // 加上装备带来的属性值增加
                    currentValue += passValue;
                    // 记录属性变化
                    roleProperty.setCurrentValue(currentValue);
                } else {
                    rolePropertiesMap.put(thingRoleProperty.getKey(),thingRoleProperty);
                }
            }
        );

        map.put(things.getPart(), things);
        log.debug(" map.put(things.getPart(), things); {}",  map);
        log.debug(" rolePropertiesMap {}", rolePropertiesMap  );
    }

    public void remove(String part) {
        map.remove(part);
    }

    public Collection<Things> list() {
        return map.values();
    }



}
