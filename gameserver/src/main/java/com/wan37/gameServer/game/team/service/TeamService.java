package com.wan37.gameServer.game.team.service;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.team.manager.TeamMnanger;
import com.wan37.gameServer.game.team.model.Team;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 12:20
 * @version 1.00
 * Description: 队伍服务
 */

@Service
public class TeamService {

    @Resource
    private TeamMnanger teamMnanger;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    /**
     *  邀请玩家加入自己的队伍
     * @param invitee 被邀请者
     * @return 是否邀请成功
     */
    public boolean inviteTeam(Player invitor,Player invitee) {
        // 如果被要求者已经有队伍了，不能组队
        if (!invitee.getTeamId().isEmpty())
            return false;

        Long invitorIdNow = teamMnanger.getTeamQequest(invitee.getId());
        // 如果当前有人正在邀请玩家，则不能组队
        if (null != invitorIdNow)
            return false;

        //保存组队请求
        teamMnanger.putTeamQequest(invitee.getId(),invitor.getId());

        return true;
    }

    public void leaveTeam(ChannelHandlerContext ctx) {
        Player leaver = playerDataService.getPlayerByCtx(ctx);
        if (leaver.getTeamId().isEmpty()) {
            notificationManager.notifyPlayer(leaver,"你并不在任何队伍里");
        } else {
            Team team = teamMnanger.get(leaver.getTeamId());
            team.getTeamPlayer().remove(leaver.getId());
            leaver.setTeamId("");
            team.getTeamPlayer().values().forEach(
                    p -> notificationManager.notifyPlayer(p, MessageFormat.format("玩家{0}已经离开队伍",leaver.getName()))
            );
            notificationManager.notifyPlayer(leaver,"你已经离开队伍");
        }
    }




    public void joinTeam(ChannelHandlerContext ctx) {
        Player invitee = playerDataService.getPlayerByCtx(ctx);
        Long invitorId = teamMnanger.getTeamQequest(invitee.getId());
        Player invitor = playerDataService.getOnlinePlayerById(invitorId);

        String invitorTeamId = invitor.getTeamId();
         // 检测发起组队的玩家是否已经有队伍
        if (invitorTeamId.isEmpty()) {
            // 新建队伍
            Map<Long,Player> playerMap = new ConcurrentHashMap<>();
            playerMap.putIfAbsent(invitor.getId(), invitor);
            playerMap.putIfAbsent(invitee.getId(),invitee) ;
            String teamId = invitor.getId().toString();
            Team team = new Team(teamId,playerMap);
            teamMnanger.put(teamId,team);
            invitee.setTeamId(teamId);
            invitor.setTeamId(teamId);
        } else {
            // 从发起组队的人获取队伍实体
            Team team = teamMnanger.get(invitorTeamId);
            team.getTeamPlayer().putIfAbsent(invitee.getId(),invitee);
            invitee.setTeamId(team.getId());
        }
    }


}
