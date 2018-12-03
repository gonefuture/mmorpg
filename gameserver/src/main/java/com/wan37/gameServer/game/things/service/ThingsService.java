package com.wan37.gameServer.game.things.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.bag.model.Bag;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.roleProperty.model.RoleProperty;
import com.wan37.gameServer.game.roleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.BufferService;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.model.ThingProperty;
import com.wan37.gameServer.game.things.model.Things;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:28
 * @version 1.00
 * Description: 物品服务，包括武器和普通物品
 */
@Slf4j
@Service
public class ThingsService {



    @Resource
    private ThingsCacheMgr thingsCacheMgr;

    @Resource
    private RolePropertyService rolePropertyService;


    @Resource
    private BufferService bufferService;







    /**
     *  加载物品的属性内容
     * @param things 物品
     */
    public void loadThingsProperties(Things things) {
        List<ThingProperty> thingProperties =  JSON.parseArray(things.getRoleProperties(),ThingProperty.class);
        if (thingProperties != null) {
            thingProperties.forEach( thingProperty -> {
                Integer rolePropertyId = thingProperty.getId();
                    if (rolePropertyId != null) {
                        RoleProperty roleProperty = rolePropertyService.
                                getRoleProperty(rolePropertyId);
                        roleProperty.setThingPropertyValue(thingProperty.getValue());
                        things.getThingRoleProperty()
                                .add(roleProperty);
                    }
                }
            );

        }
        log.debug("things.getRoleProperties() {}", things.getRoleProperties());
        log.debug("thingProperties {}", thingProperties);
    }


    /**
     *  获取物品
     * @param thingsId 物品id
     */
    public Things getThings(Integer thingsId) {
        Things things = thingsCacheMgr.get(thingsId);
        loadThingsProperties(things);
        return things;
    }

    public boolean useItem(Player player,  Integer locationIndex) {
        Bag bag = player.getBag();
        Item item = bag.getItemMap().get(locationIndex);
        if (item == null)
            return false;
        Things things = thingsCacheMgr.get(item.getThings().getId());
        Buffer buffer = bufferService.getTBuffer(things.getBuffer());
        if (buffer != null) {
            bufferService.startBuffer(player,buffer);
        }
        return true;
    }


}
