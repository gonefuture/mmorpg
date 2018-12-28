package com.wan37.gameServer.game.mission.eventHandler;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.event.Event;
import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.mission.MonsterEventDeadEvent;
import com.wan37.gameServer.game.mission.manager.MissionManager;
import com.wan37.gameServer.game.mission.model.*;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private MissionManager missionManager;







    private void killMonsterNumber(MonsterEventDeadEvent eventDeadEvent) {

        Long monsterId = eventDeadEvent.getTarget().getId();


        MissionManager.allMission().values().stream()
                .filter(m -> checkMonsterKind(m,eventDeadEvent.getTarget()))
                .forEach(
                        m ->{
                            // 获取玩家杀怪类型的任务和成就
                            MissionProgress  missionProgress = MissionManager.
                                    getMissionProgress(eventDeadEvent.getPlayer().getId(),m.getId());
                            log.debug("missionProgress {}",missionProgress);
                            // 如果玩家没有记录，现在创建记录
                            MissionProgress  mp = Optional.ofNullable(missionProgress)
                                    .orElseGet( () -> {
                                        MissionProgress missionProgressNow = new MissionProgress();
                                        missionProgressNow.setMissionId(m.getId());
                                        missionProgressNow.setPlayerId(eventDeadEvent.getPlayer().getId());
                                        missionProgressNow.setBeginTime(new Date());
                                        missionProgressNow.setMissionState(MissionState.NOT_START.getCode());
                                        // 初始化进度
                                        m.getConditionsMap().forEach(   (id,goal) ->
                                                    missionProgressNow.getProgressMap().put(id,new ProgressNumber(goal))
                                        );
                                        missionProgressNow.setProgress(JSON.toJSONString(missionProgressNow.getProgressMap()));
                                        log.debug("杀怪任务持久化missionProgressNow前 {}",missionProgressNow);
                                        // 持久化进度
                                        missionManager.saveMissionProgress(missionProgressNow);
                                        return missionProgressNow;
                                    }
                            );

                            mp.getProgressMap().forEach(
                                    (id,progressNumber) -> {
                                        if (Long.valueOf(id).equals(monsterId)) {
                                            // 当前进度加一
                                            progressNumber.getNow().incrementAndGet();
                                        }
                                    }
                            );
                            mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                            log.debug("杀怪任务更新mp前{}",mp);
                            missionManager.upDateMissionProgress(mp);
                        }
                );

    }



    private boolean checkMonsterKind(Mission mission, Monster monster) {
        if (!mission.getType().equals(MissionType.KILL_MONSTER.getTypeId()))
            return false;
        log.debug("mission.getConditionsMap() {}", mission.getConditionsMap());
        // 如果任务中有任意一个条件需要这个怪物，则是相关任务
        if (mission.getConditionsMap().keySet().stream().
                map(Long::valueOf)
                .anyMatch(id -> id.equals(monster.getId())))
            return true;

        return false;
    }





}
