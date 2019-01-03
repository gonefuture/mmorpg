package com.wan37.gameServer.event.handler;

import com.wan37.gameServer.event.EventBus;
import com.wan37.gameServer.event.model.LevelEvent;
import com.wan37.gameServer.game.mission.model.MissionCondition;
import com.wan37.gameServer.game.mission.model.MissionType;
import com.wan37.gameServer.game.mission.service.MissionService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 15:32
 * @version 1.00
 * Description: 等级事件处理
 */

@Component
public class LevelEventHandler {

    {
        EventBus.subscribe(LevelEvent.class, this::levelUp);
    }

    @Resource
    private MissionService missionService;

    @Resource
    private NotificationManager notificationManager;

    private  void levelUp(LevelEvent levelEvent) {
        notificationManager.notifyPlayer(levelEvent.getPlayer(), MessageFormat.format("恭喜你，您的等级升到了{0}级",levelEvent.getLevel()));
        missionService.checkMissionProgressByNumber(MissionType.LEVEL,levelEvent.getPlayer(),
                MissionCondition.FIRST_ACHIEVEMENT,levelEvent.getLevel());
    }




}
