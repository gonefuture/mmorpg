package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.gameServer.manager.cache.SceneCacheMgr;
import com.wan37.mysql.pojo.entity.TScene;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        TScene nowScene = sceneCacheMgr.get(player.getScene());
        String[] neighbors = nowScene.getNeighbors().split(",");
        for (String sceneId : neighbors) {
            if (String.valueOf(willMoveSceneId).equals(sceneId)) {
                // 将移动后的场景记录进玩家信息里
                player.setScene(willMoveSceneId);
                playerCacheMgr.put(channel,player);

                // 从当前场景中退出
                playerOutScene(nowScene, player);

                // 添加玩家进入场景
                TScene hasMoveToScene = sceneCacheMgr.get(willMoveSceneId);
                putPlayerInScene(hasMoveToScene,player);
                return true;
            }
        }
        return false;
    }



    public TScene currentScene(String channelId) {
        Player player =  playerCacheMgr.get(channelId);
        return sceneCacheMgr.get(player.getScene());

    }

    public void putPlayerInScene(TScene hasMoveToScene, Player player) {
        String players = hasMoveToScene.getPlayers();
        players += (","+player.getId());
        hasMoveToScene.setPlayers(players);
        sceneCacheMgr.put(hasMoveToScene.getId(),hasMoveToScene);
    }


    public void playerOutScene(TScene nowScene, Player player) {
        String players = nowScene.getPlayers();
        players = players.replaceAll(","+player.getId(),"");
        nowScene.setPlayers(players);
        sceneCacheMgr.put(nowScene.getId(),nowScene);
    }



}
