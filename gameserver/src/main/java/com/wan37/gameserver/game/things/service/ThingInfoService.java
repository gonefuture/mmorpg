package com.wan37.gameserver.game.things.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameserver.game.bag.model.Bag;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.roleProperty.model.RoleProperty;
import com.wan37.gameserver.game.roleProperty.service.RolePropertyService;
import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.BufferService;
import com.wan37.gameserver.game.things.manager.ThingsManager;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.model.ThingProperty;

import com.wan37.gameserver.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/22 16:28
 * @version 1.00
 * Description: 物品服务，包括武器和普通物品
 */
@Slf4j
@Service
public class ThingInfoService {



    @Resource
    private ThingsManager thingsManager;

    @Resource
    private RolePropertyService rolePropertyService;


    @Resource
    private BufferService bufferService;




    /**
     *  加载物品的属性内容
     * @param thingInfo 物品
     */
    public void loadThingsProperties(ThingInfo thingInfo) {
        List<ThingProperty> thingProperties =  JSON.parseArray(thingInfo.getRoleProperties(),ThingProperty.class);
        if (thingProperties != null) {
            thingProperties.forEach( thingProperty -> {
                Integer rolePropertyId = thingProperty.getId();
                    if (rolePropertyId != null) {
                        RoleProperty roleProperty = rolePropertyService.getRoleProperty(rolePropertyId);
                        roleProperty.setThingPropertyValue(thingProperty.getValue());
                        thingInfo.getThingRoleProperty()
                                .put(roleProperty.getKey(),roleProperty);
                    }
                }
            );

        }
        //log.debug("加载物品的属性内容 thingInfo.getRoleProperties() {}", thingInfo.getRoleProperties());
        //log.debug("thingProperties {}", thingProperties);
    }


    /**
     *  获取物品描述
     * @param thingInfoId 物品id
     */
    public ThingInfo getThingInfo(Integer thingInfoId) {
        return thingsManager.get(thingInfoId);

    }

    public boolean useItem(Player player, Integer locationIndex) {
        Bag bag = player.getBag();
        Item item = bag.getItemMap().get(locationIndex);
        if (item == null)
            return false;
        ThingInfo thingInfo = thingsManager.get(item.getThingInfo().getId());
        Buffer buffer = bufferService.getTBuffer(thingInfo.getBuffer());
        if (buffer != null) {
            bufferService.startBuffer(player,buffer);
        }
        return true;
    }


    /**
     *  物品条目随机数
     */
    public Long generateItemId() {
        // 使用推特的雪花算法
        SnowFlake snowFlake = new SnowFlake(1, 1);
        return snowFlake.nextId();
    }


}
