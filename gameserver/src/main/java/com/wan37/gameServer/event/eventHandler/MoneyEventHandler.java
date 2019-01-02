package com.wan37.gameServer.event.eventHandler;


import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.achievement.MoneyEvent;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:47
 * @version 1.00
 * Description: 金钱变化处理器
 */

@Component
public class MoneyEventHandler {

    {
        EventBus.subscribe(MoneyEvent.class, this::moneyNumber);
    }


    @Resource
    private MissionService missionService;

    private  void moneyNumber(MoneyEvent moneyEvent) {
        missionService.checkMissionProgress(MissionType.MONEY,moneyEvent.getPlayer(), MissionCondition.NUMBER);
    }
}
