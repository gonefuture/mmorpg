package com.wan37.gameServer.game.mission.eventHandler;

import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.mission.MonsterEventDeadEvent;
import org.springframework.stereotype.Component;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 18:28
 * @version 1.00
 * Description: mmorpg
 */


@Component
public class MonsterEventDeadEventHandler {
    
    
    
    {
        EventBus.subscribe(MonsterEventDeadEvent.class,this::killMonsterNumber);
        
    }

    private void killMonsterNumber(MonsterEventDeadEvent eventDeadEvent) {



    }


}
