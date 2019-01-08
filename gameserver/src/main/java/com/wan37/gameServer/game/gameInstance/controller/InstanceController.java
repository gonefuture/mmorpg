package com.wan37.gameServer.game.gameInstance.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameInstance.service.InstanceService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/26 16:49
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class InstanceController {

    {
        ControllerManager.add(MsgId.ENTER_INSTANCE,this::instanceEnter);
        ControllerManager.add(MsgId.EXIT_INSTANCE,this::instanceExit);

    }


    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private InstanceService instanceService;




    public void instanceEnter(ChannelHandlerContext ctx, Message message) {
        String[] cmd  = new String(message.getContent()).split("\\s+");

        Integer instanceId = Integer.valueOf(cmd[1]);


        Player player = playerDataService.getPlayer(ctx);
        GameInstance gameInstance = instanceService.enterInstance(player,instanceId);
        if (gameInstance != null) {
            message.setFlag((byte) 1);
            message.setContent(("进入副本 "+gameInstance.getName() ).getBytes());
        } else {
            message.setContent(MessageFormat.format("进入id为{}的失败", instanceId).getBytes());
        }
        ctx.writeAndFlush(message);
    }


    public void instanceExit(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx);

        if (instanceService.isInInstance(player)) {
            instanceService.exitInstance(player,(GameInstance)player.getCurrentScene());
            message.setFlag((byte) 1);
            message.setContent("退出副本成功".getBytes());
        } else {
            message.setContent("你不在副本中".getBytes());
        }
        ctx.writeAndFlush(message);
    }
}
