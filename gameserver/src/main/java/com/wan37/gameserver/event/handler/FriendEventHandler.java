package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.FriendEvent;
import com.wan37.gameserver.game.quest.model.QuestCondition;
import com.wan37.gameserver.game.quest.model.QuestType;
import com.wan37.gameserver.game.quest.service.QuestService;
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
    private QuestService questService;


    private  void addFriend(FriendEvent guildEvent) {
        questService.checkMissionProgress(QuestType.FRIEND,guildEvent.getPlayer(), QuestCondition.FIRST_ACHIEVEMENT);
    }
}
