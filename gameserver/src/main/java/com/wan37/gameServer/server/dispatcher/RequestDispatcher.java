package com.wan37.gameServer.server.dispatcher;

import com.wan37.gameServer.common.IController;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.server.cotroller.ControllerManager;
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
@Component
public class RequestDispatcher  extends SimpleChannelInboundHandler<Message> {

    @Resource
    private ControllerManager controllerManager;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        log.info(msg.toString());
        log.info("controllerManager"+controllerManager);
        IController controller = controllerManager.get(MsgId.HEllO_1000.getMsgId());
        controller.handle(null,ctx,msg);
     }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务器内部发生错误");
        throw new RuntimeException(cause.getCause());
    }
}
