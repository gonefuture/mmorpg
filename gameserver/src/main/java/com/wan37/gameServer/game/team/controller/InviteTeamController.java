package com.wan37.gameServer.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.team.service.TeamService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 15:17
 * @version 1.00
 * Description: 向一个玩家发起组队
 */

@Controller
public class InviteTeamController implements IController {



    {
        ControllerManager.add(MsgId.INVITE_TEAM,this);
    }



    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private TeamService teamService;

    @Resource
    private NotificationManager notificationManager;


    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        String[] parameter = new String(message.getContent()).split("\\s+");
        int inviteeId = Integer.valueOf(parameter[1]);
        Player invitee = playerDataService.getOnlinePlayerById(inviteeId);
        Player inviter =  playerDataService.getPlayerByCtx(ctx);
        if (teamService.inviteTeam(inviter,invitee)) {
            notificationManager.notifyByCtx(ctx,MessageFormat.format("已向玩家 {0} 发起组队请求",invitee.getName()));
            notificationManager.notifyPlayer(invitee,MessageFormat.format("玩家 {0} 向你发起发起组队请求,同意或者拒绝",inviter.getName()));
        } else {
            notificationManager.notifyByCtx(ctx,MessageFormat.format("邀请玩家 {0}组队失败，可能是因为对方已经在一个队伍里" ,invitee.getName()));
        }
    }
}
