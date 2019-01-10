package com.wan37.gameserver.game.sceneObject.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.util.ParameterCheckUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/2 11:41
 * @version 1.00
 * Description: mmorpg
 */

@Controller
public class SceneObjectController {

    {
        ControllerManager.add(MsgId.TALK_WITH_NPC,this::talkWithNPC);
    }


    @Resource
    private GameObjectService gameObjectService;



    private void talkWithNPC(ChannelHandlerContext ctx, Message message) {
        String[] args = ParameterCheckUtil.checkParameter(ctx,message,1);
        Long npcId = Long.valueOf(args[0]);
        gameObjectService.talkWithNPC(npcId,ctx);
    }

}
