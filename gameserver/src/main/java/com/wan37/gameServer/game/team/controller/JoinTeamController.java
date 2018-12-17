package com.wan37.gameServer.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.team.service.TeamService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 12:12
 * @version 1.00
 * Description: mmorpg
 */
public class JoinTeamController implements IController {



    {
        ControllerManager.add(MsgId.JOIN_TEAM,this);
    }



    @Resource
    private TeamService teamService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] parameter = new String(message.getContent()).split("\\s+");
        teamService.joinTeam(ctx);
    }
}
