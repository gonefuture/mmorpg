package com.wan37.gameserver.event.handler;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.LevelEvent;
import com.wan37.gameserver.game.quest.model.QuestCondition;
import com.wan37.gameserver.game.quest.model.QuestType;
import com.wan37.gameserver.game.quest.service.QuestService;
import com.wan37.gameserver.manager.notification.NotificationManager;
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
    private QuestService questService;

    @Resource
    private NotificationManager notificationManager;

    private  void levelUp(LevelEvent levelEvent) {
        notificationManager.notifyPlayer(levelEvent.getPlayer(), MessageFormat.format("恭喜你，您的等级升到了{0}级",levelEvent.getLevel()));
        questService.checkMissionProgressByNumber(QuestType.LEVEL,levelEvent.getPlayer(),
                QuestCondition.FIRST_ACHIEVEMENT,levelEvent.getLevel());
    }




}
