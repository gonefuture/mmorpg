package com.wan37.gameServer.game.things.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.RoleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.manager.BagsManager;
import com.wan37.gameServer.game.gameRole.model.Bags;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.BufferService;
import com.wan37.gameServer.game.skills.service.UseSkillsService;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.modle.ThingProperty;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TItem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private BagsManager bagsManager;

    @Resource
    private BufferService bufferService;




    public List<Things> getThingsByPlayerId(Player player) {
        Map<String, TItem> itemMap = bagsManager.get(player.getId()).getItemMap();
        List<Things> thingsList = new ArrayList<>();
        itemMap.values().forEach( tThings -> {
            Things things = thingsCacheMgr.get(tThings.getThingsId());
            if (things != null){
                // 获取是否已装备的状态
                things.setState(tThings.getState());
                thingsList.add(things);
            }
        });
        return thingsList;
    }


    public void loadThings(Player player) {
        List<Things> thingsList = getThingsByPlayerId(player);
        thingsList.forEach((things) -> {
            loadThingsProperties(things);
            // 类型为装备且处于穿戴状态的，放入装备栏
            if (things.getKind() == 1 && things.getState() == 1) {
                player.getEquipmentBar().add(player,things);
            } else {
                // 其他物品放背包
//                bagsManager.
//                        get(player.getId()).
//                        getThingsList().add(things);
            }
        });
    }

    /**
     *  加载物品的属性内容
     * @param things 物品
     */
    public void loadThingsProperties(Things things) {
        List<ThingProperty> thingProperties =  JSON.parseArray(things.getRoleProperties(),ThingProperty.class);
        log.debug("");
        if (thingProperties != null) {
            thingProperties.forEach( thingProperty -> {
                Integer rolePropertyId = thingProperty.getId();
                    if (rolePropertyId != null) {
                        RoleProperty roleProperty = rolePropertyService.
                                getRoleProperty(rolePropertyId);
                        roleProperty.setCurrentValue(thingProperty.getValue());
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

    public boolean useItem(Player player, String itemId) {
        Bags bags = bagsManager.get(player.getId());
        TItem tItem = bags.getItemMap().get(itemId);
        if (tItem == null)
            return false;
        Things things = thingsCacheMgr.get(tItem.getThingsId());
        Buffer buffer = bufferService.getTBuffer(things.getBuffer());
        if (buffer != null) {
            bufferService.startBuffer(player,buffer);
        }
        return true;
    }


}
