package com.wan37.gameServer.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.team.service.TeamService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 15:58
 * @version 1.00
 * Description: 玩家离开队伍
 */

@Controller
public class LeaveTeamController implements IController {


    {
        ControllerManager.add(MsgId.LEAVE_TEAM,this);
    }

    @Resource
    private TeamService teamService;



    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        teamService.leaveTeam(ctx);
    }
}
