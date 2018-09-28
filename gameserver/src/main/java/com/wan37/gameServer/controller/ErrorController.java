package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 9:33
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Component
public class ErrorController implements IController {
    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        log.debug("请求的服务不存在");
        message.setContent(("你请求的服务不存在.. 你请求的信息为： "+
                new String(message.getContent())).getBytes()
        );
        ctx.writeAndFlush(message);
    }
}
