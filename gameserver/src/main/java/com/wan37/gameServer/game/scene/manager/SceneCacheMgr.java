package com.wan37.gameServer.game.scene.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.sceneObject.service.GameObjectService;
import com.wan37.gameServer.game.scene.model.GameScene;

import com.wan37.gameServer.game.scene.model.SceneExcelUtil;
import com.wan37.gameServer.manager.cache.ICacheManager;
import com.wan37.gameServer.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 18:07
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SceneCacheMgr implements ICacheManager<Integer, GameScene> {


    // 缓存不过期
    private static Cache<Integer, GameScene> sceneCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "场景被移除, 原因是" + notification.getCause())
            ).build();



    @Resource
    private GameObjectService gameObjectService;


    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/gameScene.xlsx");
        SceneExcelUtil sceneExcelUtil = new SceneExcelUtil(path);


        Map<Integer,GameScene> gameSceneMap = sceneExcelUtil.getMap();
        for (GameScene  gameScene: gameSceneMap.values()) {

            // 初始化怪物和NPC
            gameScene = gameObjectService.initSceneObject(gameScene);

            sceneCache.put(gameScene.getId(), gameScene);
        }
        log.debug("场景资源 {}" , gameSceneMap);
        log.info("场景资源加载进缓存完毕");

    }




    @Override
    public GameScene get(Integer key) {
        return sceneCache.getIfPresent(key);
    }

    @Override
    public void put(Integer key, GameScene value) {
        sceneCache.put(key,value);
    }



    public List<GameScene> list() {
        List<GameScene> gameScenes = new ArrayList<>();
        Map<Integer,GameScene>  sceneCacheMap = sceneCache.asMap();

        for (Map.Entry <Integer,GameScene> gameSceneEntry : sceneCacheMap.entrySet()) {
            gameScenes.add(gameSceneEntry.getValue());
        }
        return gameScenes;
    }



}
