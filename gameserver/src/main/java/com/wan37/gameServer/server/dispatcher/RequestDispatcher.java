package com.wan37.gameServer.server.dispatcher;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.controller.ErrorController;
import com.wan37.gameServer.manager.ControllerManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/18 17:02
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@ChannelHandler.Sharable
@Component
public class RequestDispatcher  extends SimpleChannelInboundHandler<Message> {

    @Resource
    private ControllerManager controllerManager;

    @Resource
    private ErrorController errorController;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        log.info("收到的信息： "+msg.toString());
        IController controller = controllerManager.get(msg.getMsgId());
        if (controller == null) {
            errorController.handle(null, ctx ,msg);
        } else {
            controller.handle(null,ctx,msg);
        }
     }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务器内部发生错误");
        throw new RuntimeException(cause.getCause());
    }
}
