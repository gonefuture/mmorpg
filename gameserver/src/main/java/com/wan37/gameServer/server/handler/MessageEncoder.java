package com.wan37.gameServer.server.handler;

import com.wan37.gameServer.common.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 11:43
 * @version 1.00
 * Description: 编码器，所有数据发送出去前都要编码
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    // 填充协议实体。
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // 定义数据的长度
        int length = msg.getContent().length + 11;
        out.writeByte(-128)
                .writeInt(length)
                .writeInt(msg.getMsgId())
                .writeInt(msg.getType())
                .writeInt(msg.getFlag())
                .writeBytes(msg.getContent());

        ctx.flush();
    }
}
