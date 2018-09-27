package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 15:51
 * @version 1.00
 * Description: JavaLearn
 */

@Slf4j
@Component
public class RoleMoveController  implements IController {

    private ByteBuf in;


    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        
    }
}
