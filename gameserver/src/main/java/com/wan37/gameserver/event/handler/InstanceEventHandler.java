package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.InstanceEvent;
import com.wan37.gameserver.game.mission.model.MissionCondition;
import com.wan37.gameserver.game.mission.model.MissionType;
import com.wan37.gameserver.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 17:27
 * @version 1.00
 * Description: mmorpg
 */

@Component
public class InstanceEventHandler {

    {
        EventBus.subscribe(InstanceEvent.class,this::passInstance);
    }

    @Resource
    private MissionService missionService;


    private  void passInstance(InstanceEvent guildEvent) {
        missionService.checkMissionProgress(MissionType.INSTANCE,guildEvent.getPlayer(), MissionCondition.FIRST_ACHIEVEMENT);
    }
}
