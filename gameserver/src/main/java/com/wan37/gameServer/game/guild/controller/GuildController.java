package com.wan37.gameServer.game.guild.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.guild.service.GuildService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/19 16:47
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class GuildController  {

    // 注册分派的方法
    {
        ControllerManager.add(MsgId.GUILD_CREATE,this::guildCreate);
        ControllerManager.add(MsgId.GUILD_SHOW,this::guildShow);
        ControllerManager.add(MsgId.GUILD_JOIN,this::guildJoin);
        ControllerManager.add(MsgId.GUILD_GRANT,this::guildGrant);
        ControllerManager.add(MsgId.GUILD_DONATE,this::guildDonate);

    }


    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GuildService guildService;


    private void guildCreate(ChannelHandlerContext ctx, Message message) {
        guildService.guildCreate(ctx);

    }

    private void guildShow(ChannelHandlerContext ctx, Message message) {
        guildService.guildShow(ctx);
    }


    private void guildJoin(ChannelHandlerContext ctx, Message message) {
        guildService.guildJoin(ctx);
    }

    private void guildDonate(ChannelHandlerContext ctx, Message message) {
        guildService.guildDonate(ctx);
    }

    private void guildGrant(ChannelHandlerContext ctx, Message message) {
        guildService.guildGrant(ctx);
    }


}
