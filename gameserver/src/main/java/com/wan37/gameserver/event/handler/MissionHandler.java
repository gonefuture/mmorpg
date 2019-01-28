package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.MissionEvent;
import com.wan37.gameserver.game.quest.model.QuestProgress;
import com.wan37.gameserver.game.quest.model.QuestState;
import com.wan37.gameserver.manager.notification.NotificationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/29 10:35
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class MissionHandler {

    {
        EventBus.subscribe(MissionEvent.class,this::notificationMission);
    }


    @Resource
    private NotificationManager notificationManager;


    /**
     *  通知任务
     * @param missionEvent   任务事件
     */
    private  void notificationMission(MissionEvent missionEvent) {
       QuestProgress missionProgress = missionEvent.getMissionProgress();
       if (QuestState.COMPLETE.getCode().equals(missionProgress.getMissionState())) {
           notificationManager.notifyPlayer(missionEvent.getPlayer(), MessageFormat.format("任务{0}  达成 \n" +
                           "{1}",
                   missionEvent.getQuest().getName(),missionEvent.getQuest().getDescribe()));
       }

        if (QuestState.FINISH.getCode().equals(missionProgress.getMissionState())) {
            notificationManager.notifyPlayer(missionEvent.getPlayer(),MessageFormat.format("完成任务{0}",
                    missionEvent.getQuest().getName()));
        }

        if (QuestState.NEVER.getCode().equals(missionProgress.getMissionState())) {
            notificationManager.notifyPlayer(missionEvent.getPlayer(), MessageFormat.format("成就 {0}  达成 \n" +
                            "{1}",
                    missionEvent.getQuest().getName(),missionEvent.getQuest().getDescribe()));
        }


    }


}
