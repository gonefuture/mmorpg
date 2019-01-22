package com.wan37.gameclient.handler;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version    : 2018/6/13 11:26.
 *  说明：
 */

import com.wan37.common.entity.Message;
import com.wan37.gameclient.view.MainView;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * <pre> </pre>
 */
@Slf4j
@Component
/**  标记该类的实例可以被多个Channel共享 **/
@ChannelHandler.Sharable
public class GameClientHandler extends SimpleChannelInboundHandler<Message> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 当被通知Channel是活跃的时候
        log.debug("连接服务器成功");
    }


    /**
        在发生异常时,记录错误并关闭Channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        /**
         *       记录信息已经被接受
         *     log.info("客户端:"+ ctx.channel().id() + "接受到信息: \n"+ new String((message.getContent())));
         */

        System.out.println(new String((message.getContent())));


        MainView.output.append(new String((message.getContent())) + "\n");
        // 使用JTextArea的setCaretPosition();手动设置光标的位置为最后一行。人气颇高。使用方法也很简单
        MainView.output.setCaretPosition(MainView.output.getDocument().getLength());
    }
}
