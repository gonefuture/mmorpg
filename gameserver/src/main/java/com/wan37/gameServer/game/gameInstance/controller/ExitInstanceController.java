package com.wan37.gameServer.game.gameInstance.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameInstance.service.InstanceService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/27 16:29
 * @version 1.00
 * Description: 退出副本
 */
@Controller
public class ExitInstanceController implements IController {


    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private InstanceService instanceService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx);
        instanceService.exitInstance(player);
        message.setFlag((byte) 1);
        message.setContent("退出副本成功".getBytes());
        ctx.writeAndFlush(message);
    }
}
