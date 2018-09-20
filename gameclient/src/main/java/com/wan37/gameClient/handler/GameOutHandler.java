package com.wan37.gameClient.handler;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 18:15
 * @version 1.00
 * Description: mmorpg
 */
public class GameOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ctx.write(Unpooled.copiedBuffer("outbound的数据",CharsetUtil.UTF_8),promise);
    }
}
