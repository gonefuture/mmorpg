package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.FriendEvent;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/3 17:26
 * @version 1.00
 * Description: mmorpg
 */
@Component
public class FriendEventHandler {


    {
        EventBus.subscribe(FriendEvent.class,this::addFriend);
    }

    @Resource
    private MissionService missionService;


    private  void addFriend(FriendEvent guildEvent) {
        missionService.checkMissionProgress(MissionType.GUILD,guildEvent.getPlayer(), MissionCondition.FIRST_ACHIEVEMENT);
    }
}
