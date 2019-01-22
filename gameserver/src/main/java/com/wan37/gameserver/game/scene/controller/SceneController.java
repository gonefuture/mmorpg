package com.wan37.gameserver.game.scene.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.Cmd;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.manager.SceneCacheMgr;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.manager.controller.ControllerManager;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;


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
        ControllerManager.add(Cmd.MOVE,this::playerMove);
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
        StringBuilder sb = new StringBuilder();
        if (gameSceneService.canMoveTo(player,gameScene)) {
            // 获取当前角色所在的场景
            gameSceneService.moveToScene(player,willMoveSceneId);
            sb.append(MessageFormat.format("你到达的地方是： {0} ", gameScene.display()));
        } else {
            sb.append(MessageFormat.format("这个地点不能到：{0}",gameScene.display()));
            sb.append(MessageFormat.format("\n 当前所处的地方是 {0}",player.getCurrentScene().display()));
        }

        NotificationManager.notifyByCtx(ctx,sb);
    }
}

