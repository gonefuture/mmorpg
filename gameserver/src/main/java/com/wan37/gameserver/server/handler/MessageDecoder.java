package com.wan37.gameserver.server.handler;

import com.wan37.common.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import lombok.extern.slf4j.Slf4j;



/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/20 10:39
 * @version 1.00
 * Description: 解码器，用协议格式的长度字段校验数据帧
 */
@Slf4j
public class MessageDecoder extends LengthFieldBasedFrameDecoder {


    // maxFrameLength 数据帧的最大长度
    // lengthFieldOffset 长度字段所在位置的偏移量
    // lengthFieldLength 长度字段占用的数据长度
    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        //in.readByte();
        //log.info("收到信息的长度标志"+in.readInt());


        //已经处理了TCP的粘包拆包问题，我们需要把字节流解码为业务对象Message
        //ByteBuf buf = (ByteBuf) super.decode(ctx, in);
        ByteBuf buf = in;

        //log.info("处理粘包和拆包后"+buf);
        if (buf == null) {
            return null;
        }
        // 协议头部是一个开始的标志0xFF
        byte startFlag = buf.readByte();
        if (startFlag != -128) {
            return null;
        }
        // 长度
        int length = buf.readInt();

        //获取Message对象
        int msgId = buf.readInt();
        byte type = buf.readByte();
        byte flag = buf.readByte();


        Message message = new Message();
        message.setLength(length);
        message.setFlag(flag);
        message.setType(type);
        message.setMsgId(msgId);

        // 转化Json，暂时不写

        // 填充内容。
        // 方法一，计算剩余的数据的长度，长度为(length-11)个字节 （适合未处理了TCP的粘包拆包问题的情况）
        // 方法二，直接取得剩余数据的长度，因为已经检验了数据的长度，所以我们用这个方法
        byte[] values = new byte[buf.readableBytes()];
        buf.readBytes(values);
        message.setContent(values);

        return message;
    }
}
