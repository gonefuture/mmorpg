package com.wan37.gameServer.game.things.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.things.service.ThingsService;
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
public class UseItemController implements IController {

    @Resource
    private ThingsService thingsService;

    @Resource
    private PlayerDataService playerDataService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] command = new String(message.getContent()).split("\\s+");
        Integer locationIndex = Integer.valueOf(command[1]);
        Player player = playerDataService.getPlayer(ctx);
        boolean flag  = thingsService.useItem(player,locationIndex);
        String result ;
        if (flag) {
            message.setFlag((byte) 1);
            result = "使用物品成功";
        } else {
            message.setFlag((byte) -1);
            result = "使用物品失败,角色为拥有这个物品或其他原因";
        }
        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}
