package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.GuildEvent;
import com.wan37.gameserver.game.mission.model.QuestCondition;
import com.wan37.gameserver.game.mission.model.QuestType;
import com.wan37.gameserver.game.mission.service.MissionService;
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
        missionService.checkMissionProgress(QuestType.GUILD,guildEvent.getPlayer(), QuestCondition.FIRST_ACHIEVEMENT);
    }


}
