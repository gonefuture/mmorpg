package com.wan37.gameserver.game.mission.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.mission.service.MissionService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/26 19:44
 * @version 1.00
 * Description: mmorpg
 */


@Controller
public class MissionController {

    {
        ControllerManager.add(MsgId.MISSION_SHOW,this::missionShow);
        ControllerManager.add(MsgId.ALL_MISSION,this::allMission);
    }


    @Resource
    private MissionService missionService;


    /**
     *  显示任务成就
     * @param cxt 上下文
     * @param message 信息
     */
    private void missionShow(ChannelHandlerContext cxt, Message message) {

        missionService.missionShow(cxt);
    }


    // 显示所有任务和成就
    private void allMission(ChannelHandlerContext cxt, Message message) {
        missionService.allMissionShow(cxt);
    }




}
