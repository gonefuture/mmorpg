package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.service.RoleLoginService;
import com.wan37.mysql.pojo.entity.GameRole;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 17:09
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class GameRoleLoginController  implements IController {

    @Resource
    private RoleLoginService roleLoginService;

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        Long id = Long.valueOf(array[1]);
        GameRole gameRole = roleLoginService.login(id);
        log.debug("角色登陆返回的信息"+ gameRole);
        message.setContent(gameRole.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
