package com.wan37.gameServer.game.gameInstance.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameInstance.service.InstanceService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/26 16:49
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class EnterInstanceController implements IController {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private InstanceService instanceService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        instanceService.enterInstance(player);
    }
}
