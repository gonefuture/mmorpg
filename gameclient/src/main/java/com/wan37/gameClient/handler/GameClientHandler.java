package com.wan37.gameClient.handler;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version    : 2018/6/13 11:26.
 *  说明：
 */

import io.netty.buffer.ByteBuf;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * <pre> </pre>
 */
@Slf4j
@Component
// 标记该类的实例可以被多个Channel共享
@ChannelHandler.Sharable
public class GameClientHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当被通知Channel是活跃的时候,发送一条信息
        //ctx.writeAndFlush(Unpooled.copiedBuffer(Unpooled.copiedBuffer("客户端: " + ctx.channel().id() + "连接",CharsetUtil.UTF_8)));
    }


    /*
        在发生异常时,记录错误并关闭Channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        // 记录信息已经被接受
        log.info(
                "客户端:"+ ctx.channel().id() + "接受到信息: \n"+  byteBuf.toString(CharsetUtil.UTF_8)
        );
    }
}
