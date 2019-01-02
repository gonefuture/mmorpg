package com.wan37.gameServer.event.eventHandler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.achievement.LevelEvent;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 15:32
 * @version 1.00
 * Description: 等级事件处理
 */

@Component
public class LevelEventHandler {

    {
        EventBus.subscribe(LevelEvent.class, this::levelUp);
    }

    @Resource
    private MissionService missionService;

    private  void levelUp(LevelEvent levelEvent) {
        missionService.checkMissionProgressByNumber(MissionType.LEVEL,levelEvent.getPlayer(),
                MissionCondition.FIRST_ACHIEVEMENT,levelEvent.getLevel());
    }




}
