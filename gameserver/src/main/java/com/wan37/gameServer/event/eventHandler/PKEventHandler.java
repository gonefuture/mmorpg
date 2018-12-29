package com.wan37.gameServer.event.eventHandler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.achievement.PKEvent;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 17:27
 * @version 1.00
 * Description: pk事件处理
 */

@Component
@Slf4j
public class PKEventHandler  {

    {
        EventBus.subscribe(PKEvent.class,this::firstPKWin);
    }




    @Resource
    private MissionService missionService;

    private  void firstPKWin(PKEvent event) {
        Player player = event.getPlayer();

        // 玩家pk胜利
        if (event.isWin()) {
            missionService.checkMissionProgress(MissionType.KILL_MONSTER,player,"condition");
        }
    }


}
