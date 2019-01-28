package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.PKEvent;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.mission.model.QuestCondition;
import com.wan37.gameserver.game.mission.model.QuestType;
import com.wan37.gameserver.game.mission.service.MissionService;
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
            missionService.checkMissionProgress(QuestType.PK,player, QuestCondition.FIRST_ACHIEVEMENT);
        }
    }


}
