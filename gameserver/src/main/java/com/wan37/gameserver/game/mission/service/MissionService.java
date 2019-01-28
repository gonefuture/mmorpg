package com.wan37.gameserver.game.mission.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.MissionEvent;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.mission.manager.MissionManager;
import com.wan37.gameserver.game.mission.model.*;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
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

    @Resource
    private ThingInfoService thingInfoService;

    @Resource
    private BagsService bagsService;

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



    public void playerMissionProgressInit(Player player) {
        missionManager.loadMissionProgress(player);
    }


    /**
     *  获取相同类型的任务
     * @param missionType 任务类型
     * @return 任务类型是否相同
     */
    private Stream<Quest> getMissionType(QuestType missionType) {
        return MissionManager.allMission().values().stream().
                filter(
                mission -> mission.getType().equals(missionType.getTypeId())
        );
    }


    /**
     *  测试是任务否含涉及当前的事件条件
     * @param quest 任务
     * @param condition 事件条件
     * @return 是否含有相关条件
     */
    private boolean hasCondition(Quest quest, String condition) {
        log.debug("quest.getConditionsMap() {}", quest.getConditionsMap());
        // 如果任务中有任意一个条件需要这个条件时，则是相关任务
        return quest.getConditionsMap().keySet().stream()
                .anyMatch(id -> id.equals(condition));
    }



    private void increaseProgress(QuestProgress missionProgress, String progressId) {
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
    public boolean isMissionComplete(QuestProgress missionProgress) {
        boolean isComplete = missionProgress.getProgressMap().values().stream().
                // 当前进度的数目指标大于或等于目标数目指标
                allMatch(progressNumber -> progressNumber.getNow().get() >= progressNumber.getGoal());

        if (isComplete) {
            missionProgress.setMissionState(QuestState.COMPLETE.getCode());
            return true;
        } else {
            return false;
        }
    }


    /**
     *     获取一个任务进度，如果玩家进度尚未记录，新建一个并持久化
     * @param m 任务
     * @param player 玩家
     * @return 一个任务进度
     */
    public QuestProgress getOrCreateProgress(Quest m, Player player) {
        // 获取玩家的任务进度
        QuestProgress missionProgress = player.getMissionProgresses().get(m.getId());
        log.debug("missionProgress {}",missionProgress);
        //
        if (m.getType().equals(QuestType.KILL_MONSTER.getTypeId() )
                || m.getType().equals(QuestType.COLLECT_THINGS.getTypeId())) {
            return null;
        }

        // 如果玩家没有记录，现在创建记录
       return Optional.ofNullable(missionProgress)
                .orElseGet( () -> {
                            QuestProgress missionProgressNow = new QuestProgress();
                            missionProgressNow.setMissionId(m.getId());
                            missionProgressNow.setPlayerId(player.getId());
                            missionProgressNow.setBeginTime(new Date());
                            missionProgressNow.setQuest(m);

                            missionProgressNow.setMissionState(QuestState.NOT_START.getCode());
                            // 初始化进度
                            m.getConditionsMap().forEach(   (id,goal) ->
                                    missionProgressNow.getProgressMap().put(id,new ProgressNumber(goal))
                            );
                            missionProgressNow.setProgress(JSON.toJSONString(missionProgressNow.getProgressMap()));
                            log.debug("missionProgressNow前 {}",missionProgressNow);
                            // 将任务进度放入玩家当中并持久化进度
                            player.getMissionProgresses().put(missionProgressNow.getMissionId(),missionProgressNow);
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
    public void checkMissionProgress(QuestType missionType, Player player, String condition) {
        getMissionType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(
                m ->{
                    // 如果角色没有该任务进度，新建一个
                    QuestProgress mp = getOrCreateProgress(m,player);
                    if (mp.getMissionState().equals(QuestState.NEVER.getCode())){
                        return;
                    }

                    // 如果任务进行中
                    //if (QuestState.RUNNING.getType().equals(mp.getMissionState())) {
                        // 增加任务进度
                        increaseProgress(mp,condition);
                        //检测任务是否完成
                        if (isMissionComplete(mp)) {
                            EventBus.publish(new MissionEvent(player,m,mp));
                            // 如果任务成就是只有一次的，则设置为不再触发
                            if(QuestCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                                mp.setMissionState(QuestState.NEVER.getCode());
                            }
                        }
                        mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                        missionManager.updateMissionProgress(mp);
                    //}
                }
        );

    }

    public void checkMissionProgressByNumber(QuestType missionType, Player player, String condition, Integer nowNumber) {
        getMissionType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(   m ->{
                        // 如果角色没有该任务进度，新建一个
                        QuestProgress mp = getOrCreateProgress(m,player);
                        if (mp.getMissionState().equals(QuestState.NEVER.getCode())) {
                            return;
                        }
                        // 如果任务进行中
                        //if (QuestState.RUNNING.getType().equals(mp.getMissionState())) {
                        // 增加任务进度
                        mp.getProgressMap().get(condition).getNow().set(nowNumber);
                        int goal = mp.getProgressMap().get(condition).getGoal();

                        //检测任务是否完成
                        if (nowNumber >= goal) {
                            // 如果任务成就是只有一次的，则设置为不再触发
                            if(QuestCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                                mp.setMissionState(QuestState.NEVER.getCode());
                            } else {
                                mp.setMissionState(QuestState.COMPLETE.getCode());
                            }
                            EventBus.publish(new MissionEvent(player,m,mp));
                        }
                        mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                        log.debug("进度更新前{}",mp);
                        missionManager.updateMissionProgress(mp);
                        }
                );
    }


    public Map<Integer, QuestProgress> getPlayerMissionProgress(Player player) {

        return player.getMissionProgresses();
    }


    /**
     *  接受任务
     * @param player   玩家
     * @param missionId 任务id
     */
    public void acceptMission(Player player, Integer missionId) {
        Quest quest = MissionManager.getMission(missionId);
        if (Objects.isNull(quest)) {
            notificationManager.notifyPlayer(player,"该任务不存在");
            return;
        }

        if (Objects.isNull(player.getMissionProgresses().get(missionId))) {
            notificationManager.notifyPlayer(player,"该任务已经接过");
            return;
        }

        // 创建任务进度
        QuestProgress missionProgress = new QuestProgress();
        missionProgress.setMissionId(quest.getId());
        missionProgress.setQuest(quest);
        missionProgress.setBeginTime(new Date());
        missionProgress.setPlayerId(player.getId());
        missionProgress.setMissionState(QuestState.RUNNING.getCode());
        // 放入玩家实体中
        player.getMissionProgresses().put(missionProgress.getMissionId(),missionProgress);
        missionManager.saveOrUpdateMissionProgress(missionProgress);
    }


    /**
     * 解释任务
     * @param player    玩家
     * @param missionId 任务id
     */
    public void finishMission(Player player, Integer missionId) {
        QuestProgress missionProgress = player.getMissionProgresses().get(missionId);
        if (Objects.isNull(missionProgress)) {
            notificationManager.notifyPlayer(player,"你并没有接该任务");
            return;
        }
        if (missionProgress.getMissionState().equals(QuestState.COMPLETE.getCode())) {
            notificationManager.notifyPlayer(player,"您获得了任务奖励。");
            missionReward(player,missionProgress.getQuest());
        } else {
            notificationManager.notifyPlayer(player,"任务尚未完成");
        }

    }


    /**
     * 任务奖励
     * @param player 玩家
     * @param quest 任务
     */
    public void missionReward(Player player, Quest quest) {
        quest.getRewardThingsMap().forEach(
                (thingId, number) -> {
                    Item item = thingInfoService.createItemByThingInfo(thingId,number);
                    bagsService.addItem(player,item);
                }
        );
    }
}
