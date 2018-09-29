package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.common.ISession;
import com.wan37.gameServer.entity.User;
import com.wan37.gameServer.manager.cache.UserCacheMgr;
import com.wan37.gameServer.service.PlayerLoginService;
import com.wan37.gameServer.service.UserLoginService;
import com.wan37.mysql.pojo.entity.TPlayer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 14:25
 * @version 1.00
 * Description: mmorpg
 */
@Component
@Slf4j
public class ListGameRoleController implements IController {

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private UserCacheMgr userCacheMgr;

    @Override
    public void handle(ISession session, ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split(" ");
        User user = userCacheMgr.get(ctx.channel().id().asLongText());
        List<TPlayer> tPlayerList = userLoginService.findPlayers(user.getId());
        message.setContent(tPlayerList.toString().getBytes());
        ctx.writeAndFlush(message);
    }
}
