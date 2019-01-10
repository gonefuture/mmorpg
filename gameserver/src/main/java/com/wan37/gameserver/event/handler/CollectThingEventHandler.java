package com.wan37.gameserver.event.handler;


import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.CollectThingEvent;
import com.wan37.gameserver.game.mission.model.MissionCondition;
import com.wan37.gameserver.game.mission.model.MissionType;
import com.wan37.gameserver.game.mission.service.MissionService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 16:58
 * @version 1.00
 * Description: mmorpg
 */

@Component
public class CollectThingEventHandler {
    {
        EventBus.subscribe(CollectThingEvent.class,this::getEquipment);
        EventBus.subscribe(CollectThingEvent.class,this::getMissionThing);
    }


    @Resource
    private MissionService missionService;


    /**
     *  获取到任务物品
     * @param collectThingEvent 收集物品事件
     */
    private  void getMissionThing(CollectThingEvent collectThingEvent) {
        // 任务物品的获取加一
        missionService.checkMissionProgress(MissionType.COLLECT_THINGS,collectThingEvent.getPlayer(),
                String.valueOf(collectThingEvent.getThingInfo().getId()));
    }



    // 获取装备
    private  void getEquipment(CollectThingEvent collectThingEvent) {

        // 筛选出装备，计算装备的等级事件
        Optional<Integer> level = Optional.ofNullable(collectThingEvent.getThingInfo())
                .filter(things -> things.getKind() == 1).map(ThingInfo::getLevel);
        missionService.checkMissionProgressByNumber(MissionType.COLLECT_THINGS,collectThingEvent.getPlayer(),
                MissionCondition.FIRST_ACHIEVEMENT,level.orElse(0));
    }
}
