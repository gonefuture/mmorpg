package com.wan37.gameserver.event.handler;


import com.wan37.gameserver.event.EventBus;

import com.wan37.gameserver.event.model.MonsterEventDeadEvent;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.quest.manager.QuestManager;
import com.wan37.gameserver.game.quest.model.*;
import com.wan37.gameserver.game.quest.service.QuestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 18:28
 * @version 1.00
 * Description: mmorpg
 */


@Component
@Slf4j
public class KillMonsterEventHandler {

    {
        EventBus.subscribe(MonsterEventDeadEvent.class,this::killMonsterNumber);
    }


    @Resource
    private QuestManager missionManager;

    @Resource
    private QuestService questService;


    /**
     *  按杀死怪物标记任务进度的任务
     * @param deadEvent  怪物死亡事件
     */
    private void killMonsterNumber(MonsterEventDeadEvent deadEvent) {
        Long monsterId = deadEvent.getTarget().getId();
        Player player = deadEvent.getPlayer();

        questService.checkMissionProgress(QuestType.KILL_MONSTER,player,String.valueOf(monsterId));

    }










}
