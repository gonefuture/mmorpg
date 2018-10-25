package com.wan37.gameServer.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.service.PlayerQuitService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 17:34
 * @version 1.00
 * Description: 玩家退出控制器
 */

@Controller
public class PlayerQuitController implements IController {

    @Resource
    private PlayerQuitService playerQuitService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

        // 断开连接退出
        playerQuitService.logout(ctx);

    }
}
