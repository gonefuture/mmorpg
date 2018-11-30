package com.wan37.gameServer.game.gameRole.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/30 15:54
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class ShowPlayerController implements IController {

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

    }
}
