package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.service.UserLoginService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 10:19
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class UserLoginController implements IController {

    @Resource
    private UserLoginService userLoginService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");

        boolean flag = userLoginService.login(Long.valueOf(array[1]), array[2],
                ctx.channel().id().asLongText());
        String result = null;
        if (flag) {
            result = "登陆成功,请发送指令 1002 加载角色列表";
        } else {
           result = "登陆失败，请检查用户名或密码";
        }
        message.setContent(result.getBytes());
        ctx.writeAndFlush(message);
    }
}
