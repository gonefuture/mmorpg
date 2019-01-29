package com.wan37.gameserver.game.sceneObject.controller;

import com.wan37.common.entity.Cmd;
import com.wan37.common.entity.Message;
import com.wan37.gameserver.game.quest.model.Quest;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 11:41
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class SceneObjectController {

    {
        ControllerManager.add(Cmd.TALK_WITH_NPC,this::talkWithNPC);
        ControllerManager.add(Cmd.SHOW__NPC_QUEST,this::showNPCQuest);
    }

    @Resource
    private GameObjectService gameObjectService;

    private void talkWithNPC(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long npcId = Long.valueOf(args[1]);
        gameObjectService.talkWithNPC(npcId,ctx);
    }


    private void showNPCQuest(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,2);
        Long npcId = Long.valueOf(args[1]);
        List<Quest> questList =  gameObjectService.npcQuest(npcId);
        NotificationManager.notifyByCtx(ctx,"任务列表：");

        questList.forEach(quest -> NotificationManager.notifyByCtx(ctx, MessageFormat.format("{0} {1}",
                quest.getId(),quest.getName()
                )));
    }

}
