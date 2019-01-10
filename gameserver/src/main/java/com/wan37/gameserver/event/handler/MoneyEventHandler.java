package com.wan37.gameserver.event.handler;


import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.MoneyEvent;
import com.wan37.gameserver.game.mission.model.MissionCondition;
import com.wan37.gameserver.game.mission.model.MissionType;
import com.wan37.gameserver.game.mission.service.MissionService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 10:47
 * @version 1.00
 * Description: 金钱变化处理器
 */

@Component
public class MoneyEventHandler {

    {
        EventBus.subscribe(MoneyEvent.class, this::moneyNumber);
        EventBus.subscribe(MoneyEvent.class, this::moneyChange);
    }



    @Resource
    private MissionService missionService;

    @Resource
    private NotificationManager notificationManager;


    private  void moneyNumber(MoneyEvent moneyEvent) {
        missionService.checkMissionProgressByNumber(MissionType.MONEY,moneyEvent.getPlayer(),
                MissionCondition.FIRST_ACHIEVEMENT,moneyEvent.getPlayer().getMoney());
    }


    /**
     *  金币变化监听
     * @param moneyEvent 金币事件
     */
    private void moneyChange(MoneyEvent moneyEvent) {
        if (moneyEvent.getMoney() > 0) {
            notificationManager.notifyPlayer(moneyEvent.getPlayer(), MessageFormat.format("你的金币增加了{0}",moneyEvent.getMoney()));
        }

        if (moneyEvent.getMoney() < 0) {
            notificationManager.notifyPlayer(moneyEvent.getPlayer(), MessageFormat.format("你的金币减少了{0}",moneyEvent.getMoney()));
        }
    }

}
