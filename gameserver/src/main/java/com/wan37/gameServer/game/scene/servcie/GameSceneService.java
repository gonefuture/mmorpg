package com.wan37.gameServer.game.scene.servcie;


import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.player.service.PlayerDataService;
import com.wan37.gameServer.game.scene.model.CommonSceneId;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;
import com.wan37.gameServer.game.scene.model.SceneType;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 17:09
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Service
public class GameSceneService {


    @Resource
    private PlayerDataService playerDataService;



    /**
     *      通过
     * @param player   玩家
     * @return 空或者场景
     */
    public GameScene getSceneByPlayer(Player player) {
        player.setScene(player.getCurrentScene().getId());
        return player.getCurrentScene();
    }

    /**
     *  通过字符串的id序列查找场景
     */
    public List<GameScene> getNeighborsSceneByIds(String sceneIds) {
        List<GameScene> gameSceneList = new ArrayList<>();
        if (Objects.isNull(sceneIds) || sceneIds.isEmpty())
            return gameSceneList;
        String[] stringIds = sceneIds.split(",");
        Arrays.stream(stringIds).forEach((stringId) -> {
            Integer id = Integer.valueOf(stringId);
            GameScene gameScene = SceneCacheMgr.getScene(id);
            gameSceneList.add(gameScene);
        });
        return gameSceneList;
    }

    /**
     *  通过玩家寻找相邻场景
     * @param player 玩家
     * @return 相邻的场景
     */
    public List<GameScene> getNeighborsSceneByPlayer(Player player) {
        GameScene gameScene = getSceneByPlayer(player);
        return getNeighborsSceneByIds(gameScene.getNeighbors());
    }

    /**
     *      通过上下文查找场景
     * @param ctx 通道上下文
     * @return 该通道当前的场景
     */
    public GameScene getSceneByCtx(ChannelHandlerContext ctx) {
        Player player = playerDataService.getPlayer(ctx);
        return getSceneByPlayer(player);
    }

    /**
     *  传送进某个场景
     * @param player 玩家
     * @param sceneId 目的场景的id
     */
    public void moveToScene(Player player, int sceneId) {
        GameScene gameScene = getSceneByPlayer(player);
        // 从当前场景移除
        gameScene.getPlayers().remove(player.getId());

        player.setScene(sceneId);
        GameScene targetScene = SceneCacheMgr.getScene(sceneId);
        // 放入目的场景
        targetScene.getPlayers().put(player.getId(), player);
        player.setCurrentScene(targetScene);
    }


    /**
     *  移动到某个场景
     * @param player 玩家
     * @param gameScene 场景
     */
    public void moveToScene(Player player, GameScene gameScene) {
        // 从旧场景移除
        player.getCurrentScene().getPlayers().remove(player.getId());

        player.setScene(gameScene.getId());
        // 放入目的场景
        gameScene.getPlayers().put(player.getId(), player);
        player.setCurrentScene(gameScene);
    }




    /**
     *  进入场景初始化
     * @param player 玩家
     */
    public void initPlayerScene(Player player) {
        GameScene scene = SceneCacheMgr.getScene(player.getScene());

        // 如果玩家场景id显示在副本但是身上却没关联副本实例，返回墓地的场景
        if (scene.getType().equals(SceneType.INSTANCE_SCENE.getType()) && Objects.isNull(player.getCurrentScene())){
            GameScene cemetery = SceneCacheMgr.getScene(CommonSceneId.CEMETERY.getId());
            player.setScene(CommonSceneId.CEMETERY.getId());
            player.setCurrentScene(cemetery);
            cemetery.getPlayers().put(player.getId(),player);
            return;
        }
        scene.getPlayers().put(player.getId(),player);
        log.debug("当前场景{}",scene);
        player.setCurrentScene(scene);
    }


    /**
     *  移动到一个场景
     * @param player player
     * @param willGo 想要去的场景
     * @return 是否移动成功
     */
    public boolean canMoveTo(Player player, GameScene willGo) {
        if (!willGo.getType().equals(SceneType.COMMON_SCENE.getType()))  {
           return false;
        }
        List<GameScene> gameSceneList = getNeighborsSceneByPlayer(player);
        // 检测是否能够到达想去的地方
        return  gameSceneList.stream().anyMatch(scene -> scene.getId().equals(willGo.getId()));
    }
}
