package com.wan37.gameserver.game.guild.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.guild.service.GuildService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

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
        ControllerManager.add(Cmd.GUILD_CREATE,this::guildCreate);
        ControllerManager.add(Cmd.GUILD_SHOW,this::guildShow);
        ControllerManager.add(Cmd.GUILD_JOIN,this::guildJoin);
        ControllerManager.add(Cmd.GUILD_GRANT,this::guildGrant);
        ControllerManager.add(Cmd.GUILD_DONATE,this::guildDonate);
        ControllerManager.add(Cmd.GUILD_TAKE,this::guildTake);
        ControllerManager.add(Cmd.GUILD_PERMIT,this::guildPermit);
        ControllerManager.add(Cmd.GUILD_QUIT,this::guildQuit);

    }



    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GuildService guildService;


    private void guildCreate(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        String guildName = args[1];
        guildService.guildCreate(ctx,guildName);

    }

    private void guildShow(ChannelHandlerContext ctx, Message message) {
        guildService.guildShow(ctx);
    }


    private void guildJoin(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer guildId = Integer.valueOf(args[1]);
        guildService.guildJoin(ctx,guildId);
    }


    private void guildPermit(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long playerId = Long.valueOf(args[1]);
        guildService.guildPermit(ctx,playerId);
    }


    private void guildDonate(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer bagIndex = Integer.valueOf(args[1]);
        guildService.guildDonate(ctx,bagIndex);
    }

    private void guildGrant(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long playerId = Long.valueOf(args[1]);
        Integer guildClass = Integer.valueOf(args[2]);
        guildService.guildGrant(ctx,playerId,guildClass);
    }

    /**
     *  从公会仓库取出物品
     * @param ctx 上下文
     * @param message 信息
     */
    private void guildTake(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer wareHouseIndex = Integer.valueOf(args[1]);
        guildService.guildTake(ctx,wareHouseIndex);
    }



    private void guildQuit(ChannelHandlerContext ctx, Message message) {
        guildService.guildQuit(ctx);
    }



}
