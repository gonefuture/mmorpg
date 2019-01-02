package com.wan37.gameServer.event.eventHandler;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.achievement.StartTeamEvent;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import com.wan37.gameServer.manager.notification.NotificationManager;
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
        EventBus.subscribe(StartTeamEvent.class,this::firstTeam);
    }


    @Resource
    private NotificationManager notificationManager;

    @Resource
    private MissionService missionService;


    private  void firstTeam(StartTeamEvent event) {
        Player player = event.getPlayer();

        missionService.checkMissionProgress(MissionType.KILL_MONSTER,player, MissionCondition.FIRST_ACHIEVEMENT);


    }




}
