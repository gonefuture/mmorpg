package com.wan37.gameserver.game.quest.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.quest.manager.QuestManager;
import com.wan37.gameserver.game.quest.model.Quest;
import com.wan37.gameserver.game.quest.model.QuestKindType;
import com.wan37.gameserver.game.quest.model.QuestProgress;
import com.wan37.gameserver.game.quest.model.QuestState;
import com.wan37.gameserver.game.quest.service.QuestService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 19:44
 * @version 1.00
 * Description: mmorpg
 */


@Controller
@Slf4j
public class QuestController {

    @Resource
    private QuestService questService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private ThingInfoService thingInfoService;



    {
        ControllerManager.add(Cmd.QUEST_SHOW,this::showPlayerQuests);
        ControllerManager.add(Cmd.ACHIEVEMENT_SHOW,this::achievementShow);
        ControllerManager.add(Cmd.QUEST_ALL,this::allQuests);
        ControllerManager.add(Cmd.QUEST_ACCEPT,this::acceptQuest);
        ControllerManager.add(Cmd.QUEST_DESCRIBE,this::questDescribe);
        ControllerManager.add(Cmd.QUEST_FINISH,this::questFinish);
        ControllerManager.add(Cmd.QUEST_GIVE_UP,this::questGiveUp);

    }


    /**
     *  完成并交付任务
     */
    private void questFinish(ChannelHandlerContext cxt, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(cxt,message,2);
        Integer questId = Integer.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(cxt);
        questService.finishMission(player,questId);

    }


    /**
     *  放弃任务
     */
    private void questGiveUp(ChannelHandlerContext cxt, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(cxt,message,2);
        Integer questId = Integer.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(cxt);
        QuestProgress questProgress = questService.questGiveUp(player,questId);
        if (Objects.nonNull(questProgress)) {
            NotificationManager.notifyByCtx(cxt,"成功放弃任务");
        } else {
            NotificationManager.notifyByCtx(cxt,"放弃任务失败，任务可能尚未被接受");
        }
    }


    /**
     *  接受任务
     */
    private void acceptQuest(ChannelHandlerContext cxt, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(cxt,message,2);
        Integer missionId = Integer.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(cxt);
        questService.acceptQuest(player,missionId);

    }


    /**
     *  任务详情
     *
     */
    private void questDescribe(ChannelHandlerContext cxt, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(cxt,message,2);
        Integer questId = Integer.valueOf(args[1]);
        Quest quest = QuestManager.getQuest(questId);
        NotificationManager.notifyByCtx(cxt,MessageFormat.format("{0} {1} ",
                quest.getId(),quest.getName()));
        NotificationManager.notifyByCtx(cxt,MessageFormat.format("任务详情： {0} ",
                quest.getDescribe()));

        NotificationManager.notifyByCtx(cxt,"奖励：  ");
        quest.getRewardThingsMap().forEach( (thingId,number) ->
                        NotificationManager.notifyByCtx(cxt,MessageFormat.format("{0} x {1}",
                                Optional.ofNullable(thingInfoService.getThingInfo(thingId)).map(ThingInfo::getName),
                                number))
        );

    }


    /**
     *  显示所有任务和成就
     */

    private void allQuests(ChannelHandlerContext cxt, Message message) {
        questService.allMissionShow(cxt);
    }


    /**
     *  展示玩家当前的成就和任务进度
     */
    private void showPlayerQuests(ChannelHandlerContext cxt, Message message) {
        Player player = playerDataService.getPlayerByCtx(cxt);
        Map<Integer, QuestProgress> questProgressMap = questService.getPlayerMissionProgress(player);
        log.debug("任务 {}",questProgressMap);
        StringBuilder sb = new StringBuilder();
        sb.append("玩家当前进行的任务： \n");
        List<QuestProgress> list = questProgressMap.values().stream()
                .filter(qP -> !qP.getQuest().getKind().equals(QuestKindType.ACHIEVEMENT.getKind()))
                .filter(qP -> !qP.getQuestState().equals(QuestState.FINISH.getCode()))
                .collect(Collectors.toList());
        showQuest(list,sb);
        NotificationManager.notifyByCtx(cxt,sb);
    }


    /**
     *  展示成就
     */
    public void achievementShow(ChannelHandlerContext cxt, Message message) {
        Player player = playerDataService.getPlayerByCtx(cxt);
        Map<Integer, QuestProgress> questProgressMap = questService.getPlayerMissionProgress(player);
        StringBuilder sb = new StringBuilder();
        sb.append("玩家当前进行的成就： \n");
        List<QuestProgress> list = questProgressMap.values().stream()
                .filter(qP -> qP.getQuest().getKind().equals(QuestKindType.ACHIEVEMENT.getKind()))
                .filter(qP -> !qP.getQuestState().equals(QuestState.FINISH.getCode()))
                .collect(Collectors.toList());
        showQuest(list,sb);
        NotificationManager.notifyByCtx(cxt,sb);
    }



    private void showQuest(List<QuestProgress> questProgressList, StringBuilder sb){
        questProgressList.forEach(
                missionProgress -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}  进度: ",
                            missionProgress.getQuest().getId(),
                            missionProgress.getQuest().getName(),
                            missionProgress.getQuest().getLevel(),
                            missionProgress.getQuest().getDescribe()
                    ));
                    missionProgress.getProgressMap().forEach(
                            (k,v) -> sb.append(MessageFormat.format("{0}: {1}/{2} ", k,
                                    missionProgress.getProgressMap().get(k).getNow(),
                                    missionProgress.getProgressMap().get(k).getGoal()))
                    );
                    sb.append("\n");
                }
        );
    }


}
