package com.wan37.gameserver.game.mission.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.MissionEvent;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.mission.manager.MissionManager;
import com.wan37.gameserver.game.mission.model.*;
import com.wan37.gameserver.manager.notification.NotificationManager;
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
                (k,v) -> sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n",
                            k,v.getMission().getName(),v.getMission().getLevel(),v.getMission().getDescribe())));
        notificationManager.notifyPlayer(player,sb);
    }


    public void allMissionShow(ChannelHandlerContext cxt) {
        Player player = playerDataService.getPlayerByCtx(cxt);

        StringBuilder sb = new StringBuilder();
        sb.append("所有的任务：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() !=  4)
                .forEach( mission -> sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                            ,mission.getName(),mission.getLevel(),mission.getDescribe())));
        sb.append("\n").append("所有的成就：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() ==  4)
                .forEach(
                        mission -> sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                                    ,mission.getName(),mission.getLevel(),mission.getDescribe())));
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
    private Stream<Mission> getMissionType(MissionType missionType) {
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
    private boolean hasCondition(Mission mission, String condition) {
        log.debug("mission.getConditionsMap() {}", mission.getConditionsMap());
        // 如果任务中有任意一个条件需要这个条件时，则是相关任务
        return mission.getConditionsMap().keySet().stream()
                .anyMatch(id -> id.equals(condition));
    }



    private void increaseProgress(MissionProgress missionProgress, String progressId) {
        missionProgress.getProgressMap().forEach(
                (id,progressNumber) -> {
                    if (id.equals(progressId)) {
                        // 当前进杀死怪物进度度加一
                        progressNumber.getNow().incrementAndGet();
                        log.debug("progressNumber{}",progressNumber);
                    }
                }
        );
    }


    /**
     *  检测任务是否已经完成
     * @param missionProgress 任务进度
     * @return 是否完成
     */
    public boolean isMissionComplete(MissionProgress missionProgress) {
        boolean isComplete = missionProgress.getProgressMap().values().stream().
                // 当前进度的数目指标大于或等于目标数目指标
                allMatch(progressNumber -> progressNumber.getNow().get() >= progressNumber.getGoal());

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
       return Optional.ofNullable(missionProgress)
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
                            // 放入缓存并持久化进度
                            MissionManager.putMissionProgress(playerId,missionProgressNow);
                            missionManager.saveOrUpdateMissionProgress(missionProgressNow);
                            return missionProgressNow;
                        }
                );
    }


    /**
     *  检测进度
     * @param missionType   任务类型
     * @param player 玩家
     * @param condition 条件
     */
    public void checkMissionProgress(MissionType missionType,Player player, String condition) {
        getMissionType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(
                m ->{
                    // 如果角色没有该任务进度，新建一个
                    MissionProgress mp = getOrCreateProgress(m,player.getId());
                    if (mp.getMissionState().equals(MissionState.NEVER.getCode())){
                        return;
                    }

                    // 如果任务进行中
                    //if (MissionState.RUNNING.getType().equals(mp.getMissionState())) {
                        // 增加任务进度
                        increaseProgress(mp,condition);
                        //检测任务是否完成
                        if (isMissionComplete(mp)) {
                            EventBus.publish(new MissionEvent(player,m,mp));
                            // 如果任务成就是只有一次的，则设置为不再触发
                            if(MissionCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                                mp.setMissionState(MissionState.NEVER.getCode());
                            }
                        }
                        mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                        missionManager.updateMissionProgress(mp);
                    //}
                }
        );

    }

    public void checkMissionProgressByNumber(MissionType missionType, Player player, String condition, Integer nowNumber) {
        getMissionType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(
                        m ->{
                            // 如果角色没有该任务进度，新建一个
                            MissionProgress mp = getOrCreateProgress(m,player.getId());
                            if (mp.getMissionState().equals(MissionState.NEVER.getCode()))
                                return;

                            // 如果任务进行中
                            //if (MissionState.RUNNING.getType().equals(mp.getMissionState())) {
                            // 增加任务进度
                            mp.getProgressMap().get(condition).getNow().set(nowNumber);
                            int goal = mp.getProgressMap().get(condition).getGoal();

                            //检测任务是否完成
                            if (nowNumber >= goal) {
                                // 如果任务成就是只有一次的，则设置为不再触发
                                if(MissionCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                                    mp.setMissionState(MissionState.NEVER.getCode());
                                } else {
                                    mp.setMissionState(MissionState.COMPLETE.getCode());
                                }
                                EventBus.publish(new MissionEvent(player,m,mp));
                            }
                            mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                            log.debug("进度更新前{}",mp);
                            missionManager.updateMissionProgress(mp);

                        }
                );
    }




}
