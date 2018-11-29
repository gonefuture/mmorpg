package com.wan37.gameServer.game.scene.controller;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/11/24 22:01.
 *  说明：
 */

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;
import java.util.List;

/**
 * <pre> </pre>
 */
@Controller
public class LocationController implements IController {

    @Autowired
    PlayerDataService playerDataService;

    @Autowired
    GameSceneService gameSceneService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());

        GameScene gameScene = gameSceneService.findSceneById(player.getScene());

        List<GameScene> gameSceneList = gameSceneService.findNeighborsSceneByIds(gameScene.getNeighbors());

        String location = MessageFormat.format("当前场景是： {0} \n",gameScene.getName() );
        StringBuilder neighbors = new StringBuilder();
        neighbors.append(location);
        neighbors.append("相邻的场景是： ");
        gameSceneList.forEach(
                neighbor -> {
                    neighbors.append(MessageFormat.format("{0}: {1} ",neighbor.getId(), neighbor.getName() ));
                }
        );

        message.setFlag((byte) 1);
        message.setContent(neighbors.toString().getBytes());
        ctx.writeAndFlush(message);

    }
}
