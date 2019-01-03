package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.EquipmentEvent;
import com.wan37.gameServer.game.bag.model.Item;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import com.wan37.gameServer.game.things.model.Things;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 16:16
 * @version 1.00
 * Description: 装备事件处理器
 */
@Component
public class EquipmentEventHandler {

    {
        EventBus.subscribe(EquipmentEvent.class,this::equipmentLevel);
    }

    @Resource
    private MissionService missionService;


    private  void equipmentLevel(EquipmentEvent equipmentEvent) {

        Optional<Integer> level = equipmentEvent.getPlayer().getEquipmentBar().values().stream()
                .map(Item::getThings)
                .map(Things::getLevel)
                .reduce(Integer::sum);

        missionService.checkMissionProgressByNumber(MissionType.EQUIPMENT,equipmentEvent.getPlayer(), MissionCondition.
                FIRST_ACHIEVEMENT,level.orElse(0));
    }



}
