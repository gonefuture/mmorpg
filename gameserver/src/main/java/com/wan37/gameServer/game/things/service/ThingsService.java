package com.wan37.gameServer.game.things.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.game.RoleProperty.model.RoleProperty;
import com.wan37.gameServer.game.RoleProperty.service.RolePropertyService;
import com.wan37.gameServer.game.gameRole.manager.BagsManager;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.modle.ThingProperty;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TThings;
import com.wan37.mysql.pojo.entity.TThingsExample;

import com.wan37.mysql.pojo.mapper.TThingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private TThingsMapper tThingsMapper;

    @Resource
    private ThingsCacheMgr thingsCacheMgr;

    @Resource
    private RolePropertyService rolePropertyService;

    @Resource
    private BagsManager bagsManager;


    public List<Things> getThingsByPlayerId(long playerId) {
        TThingsExample tThingsExample = new TThingsExample();
        tThingsExample.or().andPlayerIdEqualTo(playerId);
        List<TThings> tThingsList = tThingsMapper.selectByExample(tThingsExample);
        List<Things> thingsList = new ArrayList<>();
        tThingsList.forEach( tThings -> {
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
        List<Things> thingsList = getThingsByPlayerId(player.getId());
        thingsList.forEach((things) -> {
            loadThingsProperties(things);
            // 类型为装备且处于穿戴状态的，放入装备栏
            if (things.getKind() == 1 && things.getState() == 1) {
                player.getEquipmentBar().add(player,things);
            } else {
                // 其他物品放背包
                bagsManager.
                        get(player.getId()).
                        getThingsList().add(things);
            }
        });
    }


    private void loadThingsProperties(Things things) {
        List<ThingProperty> thingProperties =  JSON.parseArray(things.getRoleProperties(),ThingProperty.class);
        if (thingProperties != null) {
            thingProperties.forEach( thingProperty -> {
                String rolePropertyId = thingProperty.getRolePropertyId();
                    if (rolePropertyId != null) {
                        RoleProperty roleProperty = rolePropertyService.
                                getRoleProperty(Integer.valueOf(rolePropertyId));
                        things.getThingRoleProperty()
                                .add(roleProperty);
                    }
                }
            );

        }
        log.debug("things.getRoleProperties() {}", things.getRoleProperties());
        log.debug("thingProperties {}", thingProperties);
    }


}
