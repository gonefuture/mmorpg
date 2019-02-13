package com.wan37.gameserver.game.quest.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.MissionEvent;
import com.wan37.gameserver.game.bag.model.Item;
import com.wan37.gameserver.game.bag.service.BagsService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.quest.manager.QuestManager;
import com.wan37.gameserver.game.quest.model.*;
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
import java.util.stream.Stream;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 10:25
 * @version 1.00
 * Description: mmorpg
 */


@Service
@Slf4j
public class QuestService {


    @Resource
    private QuestManager questManager;

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
        QuestManager.allMission().values().stream().
                filter( mission -> mission.getType() !=  4)
                .forEach( mission -> sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                            ,mission.getName(),mission.getLevel(),mission.getDescribe())));
        sb.append("\n").append("所有的成就：\n");
        QuestManager.allMission().values().stream().
                filter( mission -> mission.getType() ==  4)
                .forEach(
                        mission -> sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                                    ,mission.getName(),mission.getLevel(),mission.getDescribe())));
        notificationManager.notifyPlayer(player,sb);
    }



    public void playerMissionProgressInit(Player player) {
        questManager.loadQuestProgress(player);
    }


    /**
     *  获取相同类型的任务
     * @param missionType 任务类型
     * @return 任务类型是否相同
     */
    private Stream<Quest> getMissionByType(QuestType missionType) {
        return QuestManager.allMission().values().stream().
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
            missionProgress.setQuestState(QuestState.COMPLETE.getCode());
            return true;
        } else {
            return false;
        }
    }


    /**
     *     获取一个任务进度，如果玩家进度尚未记录，新建一个并持久化
     * @param quest 任务
     * @param player 玩家
     * @return 一个任务进度
     */
    public QuestProgress getOrCreateProgress(Quest quest, Player player) {
        // 获取玩家的任务进度
        QuestProgress missionProgress = player.getQuestProgresses().get(quest.getId());

        // 如果是成就且玩家没有记录，现在创建记录
        if (Objects.isNull(missionProgress)) {
            QuestProgress missionProgressNow = new QuestProgress();
            missionProgressNow.setQuestId(quest.getId());
            missionProgressNow.setPlayerId(player.getId());
            missionProgressNow.setBeginTime(new Date());
            missionProgressNow.setQuest(quest);

            missionProgressNow.setQuestState(QuestState.NOT_START.getCode());
            // 初始化进度
            quest.getConditionsMap().forEach(   (id,goal) ->
                    missionProgressNow.getProgressMap().put(id,new ProgressNumber(goal))
            );
            missionProgressNow.setProgress(JSON.toJSONString(missionProgressNow.getProgressMap()));

            // 将任务进度放入玩家当中并持久化进度
            player.getQuestProgresses().put(missionProgressNow.getQuestId(),missionProgressNow);
            questManager.saveOrUpdateMissionProgress(missionProgressNow);
            missionProgress = missionProgressNow;
        }

        log.debug("missionProgress {}",missionProgress);
        return missionProgress;
    }


    /**
     *  检测进度
     * @param questType   任务类型
     * @param player 玩家
     * @param condition 条件
     */
    public void checkMissionProgress(QuestType questType, Player player, String condition) {
        getMissionByType(questType)
                .filter(m -> hasCondition(m,condition))
                .forEach( m ->{
                    // 如果角色没有该任务进度，新建一个
                    QuestProgress questProgress = getOrCreateProgress(m,player);
                    if (Objects.isNull(questProgress)) {
                        return;
                    }
                    // 增加任务进度
                    increaseProgress(questProgress,condition);
                    //检测任务是否完成
                    if (isMissionComplete(questProgress)) {
                        EventBus.publish(new MissionEvent(player,m,questProgress));
                        // 如果任务成就是只有一次的，则设置为不再触发
                        if(QuestCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                            questProgress.setQuestState(QuestState.NEVER.getCode());
                        }
                    }
                    questProgress.setProgress(JSON.toJSONString(questProgress.getProgressMap()));
                    questManager.updateQuestProgress(questProgress);
                }
        );
    }

    public void checkMissionProgressByNumber(QuestType missionType, Player player, String condition, Integer nowNumber) {
        getMissionByType(missionType)
                .filter(m -> hasCondition(m,condition))
                .forEach(   quest ->{
                    // 如果是成就，没有该任务进度，新建一个
                    QuestProgress mp = getOrCreateProgress(quest,player);

                    if (Objects.isNull(mp)) {
                        return;
                    }

                    if (mp.getQuestState().equals(QuestState.NEVER.getCode())) {
                        return;
                    }
                    // 如果任务进行中
                    //if (QuestState.RUNNING.getType().equals(mp.getQuestState())) {
                    // 增加任务进度
                    mp.getProgressMap().get(condition).getNow().set(nowNumber);
                    int goal = mp.getProgressMap().get(condition).getGoal();

                    //检测任务是否完成
                    if (nowNumber >= goal) {
                        // 如果任务成就是只有一次的，则设置为不再触发
                        if(QuestCondition.FIRST_ACHIEVEMENT.equals(condition)) {
                            mp.setQuestState(QuestState.NEVER.getCode());
                        } else {
                            mp.setQuestState(QuestState.COMPLETE.getCode());
                        }
                        EventBus.publish(new MissionEvent(player,quest,mp));
                    }
                    mp.setProgress(JSON.toJSONString(mp.getProgressMap()));
                    questManager.updateQuestProgress(mp);
                    }
            );
    }


    public Map<Integer, QuestProgress> getPlayerMissionProgress(Player player) {
        return player.getQuestProgresses();
    }


    /**
     *  接受任务
     * @param player   玩家
     * @param missionId 任务id
     */
    public Quest acceptQuest(Player player, Integer missionId) {
        Quest quest = QuestManager.getQuest(missionId);
        if (Objects.isNull(quest)) {
            notificationManager.notifyPlayer(player,"该任务不存在");
            return null;
        }

        if (Objects.nonNull(player.getQuestProgresses().get(missionId))) {
            notificationManager.notifyPlayer(player,"该任务已经接过");
            return null;
        }

        // 新建创建任务进度
        QuestProgress questProgress = getOrCreateProgress(quest,player);
        if(Objects.nonNull(questProgress)) {
            notificationManager.notifyPlayer(player,MessageFormat.format("接受任务 {0}  ",
                    quest.getName()));
        }
        return quest;
    }


    /**
     * 解释任务
     * @param player    玩家
     * @param missionId 任务id
     */
    public void finishMission(Player player, Integer missionId) {
        QuestProgress questProgress = player.getQuestProgresses().get(missionId);
        if (Objects.isNull(questProgress)) {
            notificationManager.notifyPlayer(player,"你并没有接该任务");
            return;
        }
        if (questProgress.getQuestState().equals(QuestState.FINISH.getCode())) {
            notificationManager.notifyPlayer(player,"任务已经结束");
            return;
        }
        if (questProgress.getQuestState().equals(QuestState.COMPLETE.getCode())) {
            notificationManager.notifyPlayer(player,MessageFormat.format("您已完成任务：{}",
                    questProgress.getQuest().getName()));
            missionReward(player,questProgress.getQuest());
            questProgress.setQuestState(QuestState.FINISH.getCode());
            questManager.updateQuestProgress(questProgress);
            player.getQuestProgresses().remove(questProgress.getQuestId());
            notificationManager.notifyPlayer(player,"您获得了任务奖励。");
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


    /**
     *  放弃任务
     */
    public QuestProgress questGiveUp(Player player, Integer questId) {
        QuestProgress questProgress = player.getQuestProgresses().remove(questId);
        questManager.removeQuestProgress(player.getId(),questId);
        return questProgress;
    }


}
