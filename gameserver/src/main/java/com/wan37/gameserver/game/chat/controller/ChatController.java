package com.wan37.gameserver.game.chat.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.chat.service.ChatService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 17:48
 * @version 1.00
 * Description: 聊天接口
 */
@Controller
public class ChatController {


    {
        ControllerManager.add(MsgId.PUBLIC_CHAT,this::publicChat);
        ControllerManager.add(MsgId.WHISPER,this::whisper);
    }


    @Resource
    private ChatService chatService;

    @Resource
    private PlayerDataService playerDataService;


    public void publicChat(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        String words = cmd[1];

        Player player = playerDataService.getPlayerByCtx(ctx);
        chatService.publicChat(player,words);


    }



    public void  whisper(ChannelHandlerContext ctx, Message message) {
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
