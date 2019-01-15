package com.wan37.gameclient.coder;


import com.wan37.common.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 11:43
 * @version 1.00
 * Description: 编码器，所有数据发送出去前都要编码
 */
@Slf4j
public class MessageEncoder extends MessageToByteEncoder<Message> {

    // 填充协议实体。
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //log.info("客户端 编码器 " );

        // 定义数据的长度
        int length = msg.getContent().length + 11;
        msg.setLength(length);
        //log.info("信息长度： "+length);
        out.writeByte(-128)
                .writeInt(length)
                .writeInt(msg.getMsgId())
                .writeByte(msg.getType())
                .writeByte(msg.getFlag())
                .writeBytes(msg.getContent());

        //log.info("客户端编码： " + msg);
        //log.info("客户端发送的内容主体" + new String(msg.getContent()));
        ctx.flush();
    }
}
