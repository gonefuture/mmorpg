package com.wan37.gameServer.controller;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.service.BufferService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.mysql.pojo.entity.TBuffer;
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

        TBuffer  tBuffer = bufferService.getTBuffer(bufferId);
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());
        boolean flag = bufferService.startBuffer(player,tBuffer);

        String result = "";
        if (flag) {
            result = JSON.toJSONString(bufferId);
        } else {
            ErrorMsg errorMsg = new ErrorMsg("404","buffer不能使用");
        }
        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}
