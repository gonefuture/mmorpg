package com.wan37.gameserver.game.team.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.team.service.TeamService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/7 14:43
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class TeamController {




    {
        ControllerManager.add(Cmd.INVITE_TEAM,this::inviteTeam);
        ControllerManager.add(Cmd.LEAVE_TEAM,this::teamQuit);
        ControllerManager.add(Cmd.TEAM_SHOW,this::teamShow);
        ControllerManager.add(Cmd.JOIN_TEAM,this::teamJoin);

    }



    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private TeamService teamService;

    @Resource
    private NotificationManager notificationManager;


    public void inviteTeam(ChannelHandlerContext ctx, Message message) {
        String[] parameter = new String(message.getContent()).split("\\s+");
        int inviteeId = Integer.valueOf(parameter[1]);
        Player invitee = playerDataService.getOnlinePlayerById(inviteeId);
        Player inviter =  playerDataService.getPlayerByCtx(ctx);
        if (teamService.inviteTeam(inviter,invitee)) {
            NotificationManager.notifyByCtx(ctx, MessageFormat.format("已向玩家 {0} 发起组队请求",invitee.getName()));
            notificationManager.notifyPlayer(invitee,MessageFormat.format(
                    "玩家 {0} 向你发起发起组队请求,如果同意请输入 join",inviter.getName()));
        } else {
            NotificationManager.notifyByCtx(ctx,MessageFormat.format("邀请玩家 {0}组队失败，可能是因为对方已经在一个队伍里" ,invitee.getName()));
        }
    }


    public void teamQuit(ChannelHandlerContext ctx, Message message) {
        teamService.leaveTeam(ctx);
    }

    public void teamShow(ChannelHandlerContext ctx, Message message) {
        teamService.showTeam(ctx);
    }

    public void teamJoin(ChannelHandlerContext ctx, Message message) {
        teamService.joinTeam(ctx);
    }
}
