package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 17:34
 * @version 1.00
 * Description: 玩家退出控制器
 */

@Controller
public class PlayerQuitController implements IController {



    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

    }
}
