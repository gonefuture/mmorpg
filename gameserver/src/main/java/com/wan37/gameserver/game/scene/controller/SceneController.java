package com.wan37.gameserver.game.scene.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.manager.SceneCacheMgr;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.manager.controller.ControllerManager;
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
    private GameSceneService gameSceneService;

    @Resource
    private PlayerDataService playerDataService;




    public void playerMove(ChannelHandlerContext ctx, Message message) {
        String[] array = new String(message.getContent()).split("\\s+");
        int willMoveSceneId =  Integer.valueOf(array[1]);

        Player player = playerDataService.getPlayerByCtx(ctx);

        GameScene gameScene = SceneCacheMgr.getScene(willMoveSceneId);
        Map<String, Object> result = new HashMap<>();
        if (gameSceneService.canMoveTo(player,gameScene)) {
            // 获取当前角色所在的场景
            gameSceneService.moveToScene(player,willMoveSceneId);
            result.put("你到达的地方是： ", gameScene.display());
        } else {
            result.put("这个地点不能到： ",gameScene.display());
            result.put("\n 当前所处的地方是",player.getCurrentScene().display());
        }

        message.setContent(JSON.toJSONString(result).getBytes());
        ctx.writeAndFlush(message);
    }
}

