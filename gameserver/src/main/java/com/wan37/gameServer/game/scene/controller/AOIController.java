package com.wan37.gameServer.game.scene.controller;

import com.wan37.common.entity.Message;
import com.wan37.common.entity.MsgId;

import com.wan37.gameServer.game.player.model.Player;

import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.AOIService;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.controller.ControllerManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:35
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class AOIController  {


    {
        ControllerManager.add(MsgId.AOI,this::aoi);
        ControllerManager.add(MsgId.LOCATION,this::location);
    }


    @Resource
    private AOIService aoiService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService gameSceneService;






    private void aoi(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayer(ctx);


        GameScene gameScene = gameSceneService.getSceneByPlayer(player);



        List<GameScene> gameSceneList = gameSceneService.getNeighborsSceneByIds(gameScene.getNeighbors());

       StringBuilder  sb = new StringBuilder();
       sb.append("玩家 ").append(player.getName()).append("     ");
        sb.append(MessageFormat.format("    当前的位置是：{0}  {1}",gameScene.getId(),gameScene.getName())).append("\n");
        sb.append("   相邻的场景是：");
        gameSceneList.forEach(
                neighbor -> sb.append(MessageFormat.format("  {0}: {1} ",neighbor.getId(), neighbor.getName() ))
        );

        if (gameScene.getNpcs().isEmpty() && gameScene.getMonsters().isEmpty() && gameScene.getPlayers().size() == 0) {
             sb.append("我发现 ,这个地方空无一物");
        } else {

            sb.append("\n 场景内玩家: \n");
            gameScene.getPlayers().values().forEach( (p -> sb.append(p.displayData()).append("\n")));

            sb. append("场景内NPC： ").append("\n");
            gameScene.getNpcs().values().forEach( npc -> sb.append(npc.displayData()).append("\n"));

            sb. append("场景内怪物: ").append("\n");
            gameScene.getMonsters().values().forEach( monster -> sb.append(monster.displayData()).append("\n"));


        }
        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }


    private void location(ChannelHandlerContext ctx, Message message) {
        Player player = playerDataService.getPlayer(ctx);

        GameScene gameScene = gameSceneService.getSceneByPlayer(player);

        List<GameScene> gameSceneList = gameSceneService.getNeighborsSceneByIds(gameScene.getNeighbors());

        String location = MessageFormat.format("当前场景是： {0} \n",gameScene.getName() );
        StringBuilder neighbors = new StringBuilder();
        neighbors.append(location);
        neighbors.append("相邻的场景是： ");
        gameSceneList.forEach(
                neighbor -> neighbors.append(MessageFormat.format("{0}: {1} ",neighbor.getId(), neighbor.getName() ))
        );

        message.setFlag((byte) 1);
        message.setContent(neighbors.toString().getBytes());
        ctx.writeAndFlush(message);
    }


}
