package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.EquipmentEvent;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.mission.model.QuestCondition;
import com.wan37.gameserver.game.mission.model.QuestType;
import com.wan37.gameserver.game.mission.service.MissionService;
import com.wan37.gameserver.game.things.model.ThingInfo;
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
                .map(Item::getThingInfo)
                .map(ThingInfo::getLevel)
                .reduce(Integer::sum);

        missionService.checkMissionProgressByNumber(QuestType.EQUIPMENT,equipmentEvent.getPlayer(), QuestCondition.
                FIRST_ACHIEVEMENT,level.orElse(0));
    }



}
