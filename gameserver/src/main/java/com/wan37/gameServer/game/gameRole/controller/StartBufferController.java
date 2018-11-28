package com.wan37.gameServer.game.gameRole.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.Msg;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.BufferService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 12:26
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class StartBufferController implements IController {

    @Resource
    private BufferService bufferService;

    @Resource
    private PlayerDataService playerDataService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] param = new String(message.getContent()).split(" ");
        int bufferId = Integer.valueOf(param[1]);

        Buffer buffer = bufferService.getTBuffer(bufferId);
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        boolean flag = bufferService.startBuffer(player,buffer);

        String result = "";
        if (flag) {
            result = "开始使用Buffer " + JSON.toJSONString(buffer);
        } else {
            Msg errorMsg = new Msg(404,"buffer不能使用");
        }
        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}
