package com.wan37.gameServer.server.handler;

import com.wan37.common.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 17:56
 * @version 1.00
 * Description: mmorpg
 */
public class WebSocketEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        out.writeBytes(msg.getContent());
        ctx.flush();
    }
}
