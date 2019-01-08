package com.wan37.gameServer.game.scene.servcie;

import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.player.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 17:32
 * @version 1.00
 * Description: 角色移动服务
 */

@Slf4j
@Service
public class PlayerMoveService {

    @Resource
    private PlayerCacheMgr playerCacheMgr;


    @Resource
    private PlayerDataService playerDataService;


    @Resource
    private GameSceneService gameSceneService;


    public boolean moveScene(ChannelHandlerContext ctx, int willMoveSceneId) {
        Player player = playerCacheMgr.getPlayerByCtx(ctx);
        GameScene nowScene = gameSceneService.getSceneByPlayer(player);
        String[] neighbors = nowScene.getNeighbors().split(",");
        for (String sceneId : neighbors) {
            if (String.valueOf(willMoveSceneId).equals(sceneId)) {
                // 将移动后的场景记录进玩家信息里
                player.setScene(willMoveSceneId);
                playerCacheMgr.putCtxPlayer(ctx,player);

                // 从当前场景中退出
                playerOutScene(nowScene, player);

                // 添加玩家进入场景
                GameScene hasMoveToScene = SceneCacheMgr.getScene(willMoveSceneId);
                putPlayerInScene(hasMoveToScene,player);
                return true;
            }
        }
        return false;
    }



    public GameScene currentScene(ChannelHandlerContext ctx) {
        Player player =  playerDataService.getPlayerByCtx(ctx);
        return SceneCacheMgr.getScene(player.getScene());

    }

    /**
     *  玩家进入场景
     */
    public void putPlayerInScene(GameScene hasMoveToScene, Player player) {
        if (player != null && hasMoveToScene != null  ) {
            Map<Long, Player> players = hasMoveToScene.getPlayers();
            if (players != null) {
                players.put(player.getId(),player);
                hasMoveToScene.setPlayers(players);
            }
        }
    }

    /**
     *  玩家离开场景
     */
    private void playerOutScene(GameScene nowScene, Player player) {
        if (player != null && nowScene != null) {
                Map<Long,Player> players = nowScene.getPlayers();
                if (players != null) {
                    players.remove(player.getId());
                    nowScene.setPlayers(players);
                }
        }
    }



}
