package com.wan37.gameServer.game.chat.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.chat.service.ChatService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 17:48
 * @version 1.00
 * Description: 公共聊天接口
 */
@Controller
public class PublicChatController implements IController {

    @Resource
    private ChatService chatService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] cmd = new String(message.getContent()).split("\\s+");
        String words = cmd[1];

        Player player = playerDataService.getPlayerByCtx(ctx);
        chatService.publicChat(player,words);


    }
}
