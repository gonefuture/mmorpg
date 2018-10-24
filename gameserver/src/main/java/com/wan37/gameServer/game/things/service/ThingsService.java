package com.wan37.gameServer.game.things.service;

import com.wan37.gameServer.game.gameRole.modle.Player;
import com.wan37.gameServer.game.things.manager.ThingsCacheMgr;
import com.wan37.gameServer.game.things.modle.Equipment;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.mysql.pojo.entity.TThings;
import com.wan37.mysql.pojo.entity.TThingsExample;

import com.wan37.mysql.pojo.entity.TUser;
import com.wan37.mysql.pojo.mapper.TThingsMapper;
import com.wan37.mysql.pojo.mapper.TUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
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
            if (things.getType() == 1 && things.getState() == 1) {
                // 类型为装备
                Equipment equipment = new Equipment();
                BeanUtils.copyProperties(things,equipment);
                player.getEquipmentList().add(equipment);
            } else {
                player.getBags().add(things);
            }
        });
    }


}
