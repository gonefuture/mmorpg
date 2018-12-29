package com.wan37.gameServer.game.mission.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.mission.MissionEvent;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.mission.manager.MissionManager;
import com.wan37.gameServer.game.mission.model.*;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.mysql.pojo.entity.TMissionProgress;
import com.wan37.mysql.pojo.entity.TMissionProgressExample;
import com.wan37.mysql.pojo.entity.TMissionProgressKey;
import com.wan37.mysql.pojo.mapper.TMissionProgressMapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 10:25
 * @version 1.00
 * Description: mmorpg
 */


@Service
@Slf4j
public class MissionService {


    @Resource
    private MissionManager missionManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    public void missionShow(ChannelHandlerContext cxt) {
        Player player = playerDataService.getPlayerByCtx(cxt);

        StringBuilder sb = new StringBuilder();
        sb.append("玩家的当前进行的任务：\n");
        MissionManager.getMissionProgressMap(player.getId()).forEach(
                (k,v) -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n",
                            k,v.getMission().getName(),v.getMission().getLevel(),v.getMission().getDescribe()));
                }
        );

        notificationManager.notifyPlayer(player,sb);
    }


    public void allMissionShow(ChannelHandlerContext cxt) {
        Player player = playerDataService.getPlayerByCtx(cxt);

        StringBuilder sb = new StringBuilder();
        sb.append("所有的任务：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() !=  4)
                .forEach(
                mission -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                            ,mission.getName(),mission.getLevel(),mission.getDescribe()));
                }
        );
        sb.append("\n").append("所有的成就：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() ==  4)
                .forEach(
                        mission -> {
                            sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                                    ,mission.getName(),mission.getLevel(),mission.getDescribe()));
                        }
                );

        notificationManager.notifyPlayer(player,sb);
    }



    public void playerMissionProgressInit(Long playerId) {
        missionManager.loadMissionProgress(playerId);
    }


    /**
     *  获取相同类型的任务
     * @param missionType 任务类型
     * @return 任务类型是否相同
     */
    public Stream<Mission> getMissionType(MissionType missionType) {
        return MissionManager.allMission().values().stream().filter(
                mission -> mission.getType().equals(missionType.getTypeId())
        );
    }


    /**
     *  测试是任务否含涉及当前的事件条件
     * @param mission 任务
     * @param condition 事件条件
     * @return 是否含有相关条件
     */
    public boolean hasCondition(Mission mission, String condition) {
        log.debug("mission.getConditionsMap() {}", mission.getConditionsMap());
        // 如果任务中有任意一个条件需要这个条件时，则是相关任务
        return mission.getConditionsMap().keySet().stream()
                .anyMatch(id -> id.equals(condition));
    }



    public void increaseProgress(MissionProgress missionProgress,String progressId) {
        missionProgress.getProgressMap().forEach(
                (id,progressNumber) -> {
                    if (id.equals(progressId)) {
                        // 当前进杀死怪物进度度加一
                        progressNumber.getNow().incrementAndGet();
                    }
                }
        );
    }


    public boolean isMissionComplete(MissionProgress missionProgress) {
        boolean isComplete = missionProgress.getProgressMap().values().stream().
                allMatch(progressNumber -> progressNumber.getGoal().equals(progressNumber.getNow().get()));

        if (isComplete) {
            missionProgress.setMissionState(MissionState.COMPLETE.getCode());
            return true;
        } else {
            return false;
        }
    }


    /**
     *     获取一个任务进度，如果玩家进度尚未记录，新建一个并持久化
     * @param m 任务
     * @param playerId 玩家id
     * @return 一个任务进度
     */
    public MissionProgress getOrCreateProgress(Mission m, Long playerId) {
        // 获取玩家的任务进度
        MissionProgress  missionProgress = MissionManager.
                getMissionProgress(playerId,m.getId());
        log.debug("missionProgress {}",missionProgress);
        // 如果玩家没有记录，现在创建记录
        MissionProgress  mp = Optional.ofNullable(missionProgress)
                .orElseGet( () -> {
                            MissionProgress missionProgressNow = new MissionProgress();
                            missionProgressNow.setMissionId(m.getId());
                            missionProgressNow.setPlayerId(playerId);
                            missionProgressNow.setBeginTime(new Date());

                            missionProgressNow.setMissionState(MissionState.NOT_START.getCode());
                            // 初始化进度
                            m.getConditionsMap().forEach(   (id,goal) ->
                                    missionProgressNow.getProgressMap().put(id,new ProgressNumber(goal))
                            );
                            missionProgressNow.setProgress(JSON.toJSONString(missionProgressNow.getProgressMap()));
                            log.debug("missionProgressNow前 {}",missionProgressNow);
                            // 持久化进度
                            missionManager.saveMissionProgress(missionProgressNow);
                            return missionProgressNow;
                        }
                );
        return  mp;
    }



    public void checkMissionProgress(MissionType missionType,Player player, String condition) {
        getMissionType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(
                m ->{
                    // 如果角色没有该任务进度，新建一个
                    MissionProgress mp = getOrCreateProgress(m,player.getId());

                    // 如果任务进行中
                    //if (MissionState.RUNNING.getCode().equals(mp.getMissionState())) {
                        // 增加任务进度
                        increaseProgress(mp,condition);
                        //检测任务是否完成
                        if (isMissionComplete(mp)) {
                            EventBus.publish(new MissionEvent(player,m,mp));
                        }

                        mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                        log.debug("杀怪任务更新mp前{}",mp);
                        missionManager.upDateMissionProgress(mp);
                    //}
                }
        );

    }





}
