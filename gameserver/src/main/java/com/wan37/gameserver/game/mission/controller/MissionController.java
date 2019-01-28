package com.wan37.gameserver.game.mission.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.mission.model.QuestProgress;
import com.wan37.gameserver.game.mission.service.MissionService;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 19:44
 * @version 1.00
 * Description: mmorpg
 */


@Controller
public class MissionController {

    @Resource
    private MissionService missionService;


    @Resource
    private PlayerDataService playerDataService;


    {
        ControllerManager.add(Cmd.MISSION_SHOW,this::showPlayerMission);
        ControllerManager.add(Cmd.ALL_MISSION,this::allMission);
        ControllerManager.add(Cmd.MISSION_ACEEPT,this::acceptMission);
    }


    /**
     *  接受任务
     */
    private void acceptMission(ChannelHandlerContext cxt, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(cxt,message,2);
        Integer missionId = Integer.valueOf(args[1]);
        Player player = playerDataService.getPlayerByCtx(cxt);
        missionService.acceptMission(player,missionId);

    }





    /**
     *  显示所有任务和成就
     */

    private void allMission(ChannelHandlerContext cxt, Message message) {
        missionService.allMissionShow(cxt);
    }



    private void showPlayerMission(ChannelHandlerContext cxt, Message message) {
        Player player = playerDataService.getPlayerByCtx(cxt);
        Map<Integer, QuestProgress> missionProgressMap = missionService.getPlayerMissionProgress(player);
        StringBuilder sb = new StringBuilder();
        sb.append("玩家当前进行的任务： \n");
        missionProgressMap.values().forEach(
                missionProgress -> {
                    sb.append(MessageFormat.format("{0} {1} 等级：{2} 描述：{3}  进度: ",
                            missionProgress.getQuest().getId(),
                            missionProgress.getQuest().getName(),
                            missionProgress.getQuest().getLevel(),
                            missionProgress.getQuest().getDescribe()
                            ));
                    missionProgress.getQuest().getConditionsMap().forEach(
                            (k,v) -> sb.append(MessageFormat.format("{0}: {1}/{2} \n",k,
                                    missionProgress.getProgressMap().get(k).getNow(),
                                    missionProgress.getProgressMap().get(k).getGoal()))
                    );
                }
        );
        NotificationManager.notifyByCtx(cxt,sb);
    }



}
