package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.TalkWithEvent;
import com.wan37.gameserver.game.quest.model.QuestType;
import com.wan37.gameserver.game.quest.service.QuestService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 11:46
 * @version 1.00
 * Description: 谈话事件
 */

@Component
public class TalkWithEventHandler {

    {
        EventBus.subscribe(TalkWithEvent.class,this::talkWithNPC);
    }


    @Resource
    private QuestService questService;

    private  void talkWithNPC(TalkWithEvent event) {
        questService.checkMissionProgress(QuestType.TALK_WITH,event.getPlayer(), String.valueOf(event.getSceneObjectId()));
    }

}
