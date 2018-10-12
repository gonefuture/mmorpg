package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.gameServer.manager.cache.SceneCacheMgr;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private SceneCacheMgr sceneCacheMgr;

    @Resource
    private PlayerDataService playerDataService;


    public boolean moveScene(String channel,int willMoveSceneId) {
        Player player = playerCacheMgr.get(channel);
        GameScene nowScene = sceneCacheMgr.get(player.getScene());
        String[] neighbors = nowScene.getNeighbors().split(",");
        for (String sceneId : neighbors) {
            if (String.valueOf(willMoveSceneId).equals(sceneId)) {
                // 将移动后的场景记录进玩家信息里
                player.setScene(willMoveSceneId);
                playerCacheMgr.put(channel,player);

                // 从当前场景中退出
                playerOutScene(nowScene, player);

                // 添加玩家进入场景
                GameScene hasMoveToScene = sceneCacheMgr.get(willMoveSceneId);
                putPlayerInScene(hasMoveToScene,player);
                return true;
            }
        }
        return false;
    }



    public GameScene currentScene(String channelId) {
        Player player =  playerCacheMgr.get(channelId);
        return sceneCacheMgr.get(player.getScene());

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
            sceneCacheMgr.put(hasMoveToScene.getId(),hasMoveToScene);
        }
    }

    /**
     *  玩家离开场景
     */
    public void playerOutScene(GameScene nowScene, Player player) {
        if (player != null && nowScene != null) {
                Map<Long,Player> players = nowScene.getPlayers();
                if (players != null) {
                    players.remove(player.getId());
                    nowScene.setPlayers(players);
                }
                sceneCacheMgr.put(nowScene.getId(),nowScene);
        }
    }



}
