package com.wan37.gameserver.game.things.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/1 16:33
 * @version 1.00
 * Description: mmorpg
 */
@Controller
public class ItemController {


    {
        ControllerManager.add(Cmd.USE_ITEM,this::useItem);
    }

    @Resource
    private ThingInfoService thingInfoService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    private void useItem(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Integer locationIndex = Integer.valueOf(command[1]);
        Player player = playerDataService.getPlayer(ctx);
        boolean flag  = thingInfoService.useItem(player,locationIndex);
        String result ;
        if (flag) {
            result = "使用物品成功";
        } else {
            result = "使用物品失败,角色为拥有这个物品或其他原因";
        }

        notificationManager.notifyPlayer(player,result);
    }
}
