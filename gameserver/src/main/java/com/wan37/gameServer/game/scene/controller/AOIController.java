package com.wan37.gameServer.game.scene.controller;

import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;

import com.wan37.gameServer.game.sceneObject.model.Monster;

import com.wan37.gameServer.game.player.model.Player;

import com.wan37.gameServer.game.sceneObject.model.NPC;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.AOIService;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:35
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class AOIController implements IController {

    @Resource
    private AOIService aoiService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private GameSceneService gameSceneService;

    @Override
    public void handle(ChannelHandlerContext ctx, Message message) {

        Player player = playerDataService.getPlayer(ctx);

        int sceneId = player.getScene();
        // 分别获取场景内的玩家和游戏对象
        Map<Long,NPC> npCMap = aoiService.getNPCs(sceneId );
        Map<Long,Monster> monsterMap = aoiService.getMonsters(player,sceneId);
        List<Player> playerList = aoiService.getPlayerInScene(sceneId );
        GameScene gameScene = gameSceneService.findSceneById(sceneId);

        List<GameScene> gameSceneList = gameSceneService.findNeighborsSceneByIds(gameScene.getNeighbors());

       StringBuilder  sb = new StringBuilder();
       sb.append("玩家 ").append(player.getName()).append("     ");
        sb.append(MessageFormat.format("    当前的位置是：{0}  {1}",gameScene.getId(),gameScene.getName())).append("\n");
        sb.append("   相邻的场景是：");
        gameSceneList.forEach(
                neighbor -> {
                    sb.append(MessageFormat.format("  {0}: {1} ",neighbor.getId(), neighbor.getName() ));
                }
        );

        if (npCMap.isEmpty() && monsterMap.isEmpty() && playerList.size() == 0) {
             sb.append("我发现 ,这个地方空无一物");
        } else {

            sb.append("\n 场景内玩家: \n");
            playerList.forEach( (p -> {
                sb.append(p.displayData()).append("\n");
            }));
            sb. append("场景内NPC： ").append("\n");
            npCMap.values().forEach( npc -> {
                sb.append(npc.displayData()
                ).append("\n");
            });

            sb. append("场景内怪物: ").append("\n");
            monsterMap.values().forEach( monster -> {
                sb.append(monster.displayData()
                ).append("\n");
            });


        }
        log.debug("当前场景的玩家对象有{}个，分别为{} ",playerList.size(),playerList);
        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }


}
