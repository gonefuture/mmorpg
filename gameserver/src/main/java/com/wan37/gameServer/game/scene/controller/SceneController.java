package com.wan37.gameServer.game.scene.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import com.wan37.gameServer.game.scene.servcie.PlayerMoveService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 15:51
 * @version 1.00
 * Description: JavaLearn
 */

@Slf4j
@Component
public class
SceneController  {


    {
        ControllerManager.add(MsgId.MOVE,this::playerMove);
    }

    @Resource
    private PlayerMoveService playerMoveService;

    @Resource
    private SceneCacheMgr sceneCacheMgr;


    public void playerMove(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split("\\s+");
        int willMoveSceneId =  Integer.valueOf(array[1]);

        GameScene gameScene = sceneCacheMgr.get(willMoveSceneId);
        Map<String, Object> result = new HashMap<>();
        if (playerMoveService.moveScene(ctx,willMoveSceneId)) {
            // 获取当前角色所在的场景

            result.put("你到达的地方是： ", gameScene.display());
        } else {
            result.put("这个地点不能到： ",gameScene.display());
            GameScene currentScene = playerMoveService.currentScene(ctx);
            result.put("\n 当前所处的地方是",currentScene.display());
        }

        message.setContent(JSON.toJSONString(result).getBytes());
        ctx.writeAndFlush(message);
    }
}

