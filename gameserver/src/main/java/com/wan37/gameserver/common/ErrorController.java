package com.wan37.gameserver.common;

import com.wan37.common.entity.Message;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 9:33
 * @version 1.00
 * Description: 错误命令处理
 */
@Slf4j
@Component
public class ErrorController implements IController {
    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        log.debug("请求的服务不存在,请求内容为 {}",message.getContent());
        NotificationManager.notifyByCtx(ctx, MessageFormat.format("你请求的服务不存在.. 你请求的信息为：` {0} `",
                message.getContent()));
    }
}
