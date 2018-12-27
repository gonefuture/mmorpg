package com.wan37.gameServer.game.mission.service;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.mission.manager.MissionManager;
import com.wan37.gameServer.game.mission.model.Mission;
import com.wan37.gameServer.game.mission.model.MissionProgress;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.mysql.pojo.entity.TMissionProgress;
import com.wan37.mysql.pojo.entity.TMissionProgressExample;
import com.wan37.mysql.pojo.entity.TMissionProgressKey;
import com.wan37.mysql.pojo.mapper.TMissionProgressMapper;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/27 10:25
 * @version 1.00
 * Description: mmorpg
 */


@Service
public class MissionService {

    @Resource
    private TMissionProgressMapper tMissionProgressMapper;

    @Resource
    private MissionManager missionManager;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;


    public void missionShow(ChannelHandlerContext cxt) {
        Player player = playerDataService.getPlayerByCtx(cxt);

        StringBuilder sb = new StringBuilder();
        sb.append("玩家的当前进行的任务：\n");
        MissionManager.getMissionProgressMap(player.getId()).forEach(
                (k,v) -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n",
                            k,v.getMission().getName(),v.getMission().getLevel(),v.getMission().getDescribe()));
                }
        );

        notificationManager.notifyPlayer(player,sb);
    }


    public void allMissionShow(ChannelHandlerContext cxt) {
        Player player = playerDataService.getPlayerByCtx(cxt);

        StringBuilder sb = new StringBuilder();
        sb.append("所有的任务：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() !=  4)
                .forEach(
                mission -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                            ,mission.getName(),mission.getLevel(),mission.getDescribe()));
                }
        );
        sb.append("\n").append("所有的成就：\n");
        MissionManager.allMission().values().stream().
                filter( mission -> mission.getType() ==  4)
                .forEach(
                        mission -> {
                            sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}\n"
                                    ,mission.getName(),mission.getLevel(),mission.getDescribe()));
                        }
                );

        notificationManager.notifyPlayer(player,sb);
    }



    public void playerMissionProgressInit(Long playerId) {
        missionManager.loadMissionProgress(playerId);
//        MissionProgress playerMissionProgress = MissionManager.getMissionProgress(playerId);

//        MissionManager.allMission().values().stream().
//                // 过滤出类型为成就的任务
//                filter( mission -> mission.getType() == 4)
//                .forEach(mission ->
//                        Optional.ofNullable(playerMissionProgress).ifPresent(playerMissionProgress.);
//        );
    }
}
