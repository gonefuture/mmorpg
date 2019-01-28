package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.TeamEvent;
import com.wan37.gameserver.game.quest.model.QuestCondition;
import com.wan37.gameserver.game.quest.model.QuestType;
import com.wan37.gameserver.game.quest.service.QuestService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 15:05
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class TeamEventHandler {

    {
        EventBus.subscribe(TeamEvent.class,this::firstTeam);
    }


    @Resource
    private NotificationManager notificationManager;

    @Resource
    private QuestService questService;


    private  void firstTeam(TeamEvent teamEvent) {
        // 检测队伍是否是第一次组队
        teamEvent.getTeammate().forEach(
                p -> questService.checkMissionProgress(QuestType.TEAM,p, QuestCondition.FIRST_ACHIEVEMENT)
        );

    }




}
