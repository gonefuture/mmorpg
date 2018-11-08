package com.wan37.gameServer.game.scene.controller;

import com.alibaba.fastjson.JSON;
import com.wan37.common.entity.Message;
import com.wan37.gameServer.common.IController;
import com.wan37.gameServer.game.SceneObject.model.Monster;
import com.wan37.gameServer.game.SceneObject.model.NPC;
import com.wan37.gameServer.game.gameRole.model.Player;

import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.AOIService;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

        Player player = playerDataService.getPlayer(ctx.channel().id().asLongText());

        int sceneId = player.getScene();
        // 分别获取场景内的玩家和游戏对象
        Map<Long,NPC> npCMap = aoiService.getNPCs(sceneId );
        Map<Long,Monster> monsterMap = aoiService.getMonsters(sceneId);
        List<Player> playerList = aoiService.getPlayerInScene(sceneId );
        GameScene gameScene = gameSceneService.findTScene(sceneId);

       StringBuilder  sb = new StringBuilder();
        if (npCMap.isEmpty() && monsterMap.isEmpty() && playerList.size() == 0) {
             sb.append("我发现 ,这个地方空无一物");
        } else {
            sb.append("当前位置是： ").append(gameScene).append("\n");
            sb.append("场景内玩家: ");
            playerList.forEach( (p -> {
                sb.append(JSON.toJSONString(p)).append("\n");
            }));
            sb. append("场景内NPC： ").append(JSON.toJSONString(npCMap)).append("\n");
            sb. append("场景内怪物: ").append(JSON.toJSONString(monsterMap)).append("\n");
        }
        log.debug("当前场景的玩家对象有{}个，分别为{} ",playerList.size(),playerList);
        message.setFlag((byte)1);
        message.setContent(sb.toString().getBytes());
        ctx.writeAndFlush(message);
    }


}
