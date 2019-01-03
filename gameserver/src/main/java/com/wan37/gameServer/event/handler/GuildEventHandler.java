package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.GuildEvent;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 18:30
 * @version 1.00
 * Description: mmorpg
 */

@Component
public class GuildEventHandler {

    {
        EventBus.subscribe(GuildEvent.class,this::joinGuild);
    }

    @Resource
    private MissionService missionService;


    private  void joinGuild(GuildEvent guildEvent) {
        missionService.checkMissionProgress(MissionType.GUILD,guildEvent.getPlayer(), MissionCondition.FIRST_ACHIEVEMENT);
    }


}
