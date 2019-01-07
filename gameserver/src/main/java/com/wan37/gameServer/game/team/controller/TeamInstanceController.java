package com.wan37.gameServer.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.team.service.TeamService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 11:30
 * @version 1.00
 * Description: 开始团队副本
 */

@Controller
public class TeamInstanceController implements IController {

    {
        ControllerManager.add(MsgId.TEAM_INSTANCE,this);
    }


    @Resource
    private TeamService teamService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer instanceId = Integer.valueOf(args[1]);
        teamService.teamInstance(ctx,instanceId);
    }
}
