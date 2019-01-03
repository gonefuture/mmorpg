package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.MissionEvent;
import com.wan37.gameServer.game.mission.model.MissionProgress;
import com.wan37.gameServer.game.mission.model.MissionState;
import com.wan37.gameServer.manager.notification.NotificationManager;
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
       MissionProgress missionProgress = missionEvent.getMissionProgress();
       if (MissionState.COMPLETE.getCode().equals(missionProgress.getMissionState())) {
           notificationManager.notifyPlayer(missionEvent.getPlayer(), MessageFormat.format("任务{0}  达成 \n" +
                           "{1}",
                   missionEvent.getMission().getName(),missionEvent.getMission().getDescribe()));
       }

        if (MissionState.FINISH.getCode().equals(missionProgress.getMissionState())) {
            notificationManager.notifyPlayer(missionEvent.getPlayer(),MessageFormat.format("完成任务{0}",
                    missionEvent.getMission().getName()));
        }

        if (MissionState.NEVER.getCode().equals(missionProgress.getMissionState())) {
            notificationManager.notifyPlayer(missionEvent.getPlayer(), MessageFormat.format("成就 {0}  达成 \n" +
                            "{1}",
                    missionEvent.getMission().getName(),missionEvent.getMission().getDescribe()));
        }


    }


}
