package com.wan37.gameserver.game.player.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.BufferService;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 12:26
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class BufferController {

    {
        ControllerManager.add(Cmd.START_BUFFER,this::startBuffer);
    }

    @Resource
    private BufferService bufferService;

    @Resource
    private PlayerDataService playerDataService;


    public void startBuffer(ChannelHandlerContext ctx, Message message) {
        String[] param = new String(message.getContent()).split("\\s+");
        int bufferId = Integer.valueOf(param[1]);

        Buffer buffer = bufferService.getTBuffer(bufferId);
        Player player = playerDataService.getPlayer(ctx);
        boolean flag = bufferService.startBuffer(player,buffer);

        String result ;
        if (flag) {
            result = MessageFormat.format( "开始使用Buffer {}",buffer.getName());
        } else {
            result = "buffer不能使用";
        }
        NotificationManager.notifyByCtx(ctx,result);
    }
}
