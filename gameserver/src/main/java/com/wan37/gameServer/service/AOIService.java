package com.wan37.gameServer.service;

import com.alibaba.fastjson.JSON;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.entity.SceneActor;
import com.wan37.gameServer.manager.cache.GameObjectCacheMgr;
import com.wan37.gameServer.manager.cache.PlayerCacheMgr;
import com.wan37.gameServer.manager.cache.SceneCacheMgr;
import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TScene;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/29 19:36
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class AOIService {

    @Resource
    private SceneCacheMgr sceneCacheMgr;

    @Resource
    private GameObjectCacheMgr gameObjectCacheMgr;

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    public List<TGameObject> aoi(int sceneId) {
        TScene tScene= sceneCacheMgr.get(sceneId);
        String[]  gameObjectIdList = tScene.getGameObjectIds().split(",");
        List<TGameObject> tGameObjectList = new ArrayList<>();

        Arrays.stream(gameObjectIdList).forEach( gameObjectId -> {
            TGameObject tGameObject = gameObjectCacheMgr.get(gameObjectId);
            tGameObjectList.add(tGameObject);
        } );
        return tGameObjectList;
    }

    public List<Player> getPlayerInScene(int sceneId) {
        List<Player> playerList = new ArrayList<>();
        TScene tScene = sceneCacheMgr.get(sceneId);
        String[]  playerIdList = tScene.getPlayers().split(",");

        Arrays.stream(playerIdList).forEach( playerId -> {
            Player player =   playerCacheMgr.get(playerId);
            playerList.add(player);
        });

        return playerList;
    }

}
