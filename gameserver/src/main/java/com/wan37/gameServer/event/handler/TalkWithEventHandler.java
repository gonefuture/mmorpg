package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.TalkWithEvent;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
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
    private MissionService missionService;

    private  void talkWithNPC(TalkWithEvent event) {
        missionService.checkMissionProgress(MissionType.TALK_WITH,event.getPlayer(), String.valueOf(event.getSceneObjectId()));
    }

}
