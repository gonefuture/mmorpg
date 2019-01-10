package com.wan37.gameserver.game.team.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.manager.SceneCacheMgr;
import com.wan37.gameserver.game.team.manager.TeamManager;
import com.wan37.gameserver.game.team.model.Team;
import com.wan37.gameserver.game.team.service.TeamService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 11:30
 * @version 1.00
 * Description: 开始团队副本
 */

@Controller
public class TeamInstanceController  {

    {
        ControllerManager.add(MsgId.TEAM_INSTANCE,this::teamInstance);
    }


    @Resource
    private TeamService teamService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    private void teamInstance(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Integer instanceId = Integer.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(ctx);
        Team team = TeamManager.getTeam(player.getTeamId());
        if (Objects.isNull(team)) {
            notificationManager.notifyPlayer(player,"你没有队伍");
            return;
        }
        if (!player.getId().equals(team.getCaptainId())) {
            notificationManager.notifyPlayer(player,"你不是队长，无法开始副本");
            return;
        }
        notificationManager.notifyPlayer(player, MessageFormat.format("开始进入副本{0}",
               SceneCacheMgr.getScene(instanceId).getName()));
        teamService.teamInstance(team,instanceId);
    }
}
