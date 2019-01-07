package com.wan37.gameServer.game.chat.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.chat.service.ChatService;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 17:48
 * @version 1.00
 * Description: 私聊接口
 */
@Controller
public class WhisperController implements IController {

    @Resource
    private ChatService chatService;

    @Resource
    private PlayerDataService playerDataService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        Integer targetId = Integer.valueOf(cmd[1]);
        String words = cmd[2];
        Player player= playerDataService.getPlayerByCtx(ctx);

        Msg msg = chatService.whisper(player,targetId,words);

        message.setFlag((byte) 1);
        message.setContent(msg.getMsg().getBytes());
        ctx.writeAndFlush(message);
    }
}
