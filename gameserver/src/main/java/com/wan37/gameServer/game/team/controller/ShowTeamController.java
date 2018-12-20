package com.wan37.gameServer.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.team.service.TeamService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 9:46
 * @version 1.00
 * Description: 查看队伍
 */

@Controller
public class ShowTeamController  implements IController {

    {
        ControllerManager.add(MsgId.SHOW_TEAM,this);
    }

    @Resource
    private TeamService teamService;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        teamService.showTeam(ctx);

    }
}