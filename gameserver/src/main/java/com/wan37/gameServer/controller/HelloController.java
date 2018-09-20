package com.wan37.gameServer.controller;

import com.wan37.gameServer.common.Controller;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.common.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 15:15
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
public class HelloController implements Controller {

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        log.info("hello");
        log.info("收到的内容是： "+ new String(message.getContent()));
    }

    public static void hello() {

    }
}
