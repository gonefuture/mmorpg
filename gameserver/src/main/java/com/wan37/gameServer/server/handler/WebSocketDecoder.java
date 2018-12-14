package com.wan37.gameServer.server.handler;

import com.wan37.common.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/14 17:55
 * @version 1.00
 * Description: mmorpg
 */


@Slf4j
public class WebSocketDecoder extends LengthFieldBasedFrameDecoder {

    public WebSocketDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {


        Message message = new Message();
        // 填充内容。
        // 方法一，计算剩余的数据的长度，长度为(length-11)个字节 （适合未处理了TCP的粘包拆包问题的情况）
        // 方法二，直接取得剩余数据的长度，因为已经检验了数据的长度，所以我们用这个方法
        byte[] values = new byte[in.readableBytes()];
        in.readBytes(values);
        message.setContent(values);


        log.info("收到的内容： "+new String(message.getContent()));
        return message;
    }
}
