package com.wan37.gameServer.common;

import com.wan37.common.entity.Message;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 12:29
 * @version 1.00
 * Description: 控制器
 */

@FunctionalInterface
public interface IController {

    // 接收派发的数据，处理业务
    void handle(ISession session, ChannelHandlerContext ctx, Message message);

}
