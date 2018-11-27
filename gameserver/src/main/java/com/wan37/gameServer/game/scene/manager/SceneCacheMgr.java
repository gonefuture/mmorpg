package com.wan37.gameServer.game.scene.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.gameSceneObject.model.NPC;
import com.wan37.gameServer.game.gameSceneObject.service.GameObjectService;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.scene.model.GameScene;

import com.wan37.gameServer.game.scene.model.SceneExcelUtil;
import com.wan37.gameServer.manager.cache.GameCacheManager;
import com.wan37.gameServer.util.FileUtil;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
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
public class SceneCacheMgr implements GameCacheManager<Integer, GameScene> {


    // 缓存不过期
    private static Cache<Integer, GameScene> sceneCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "was removed, cause is" + notification.getCause())
            ).build();



    @Resource
    private GameObjectService gameObjectService;


    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/gameScene.xlsx");
        SceneExcelUtil sceneExcelUtil = new SceneExcelUtil(path);


        Map<Integer,GameScene> gameSceneMap = sceneExcelUtil.getMap();
        for (GameScene  gameScene: gameSceneMap.values()) {

            String[] ids = gameScene.getGameObjectIds().split(",");

            Arrays.stream(ids).forEach(
                    (idString) ->{
                        if (Strings.isNotBlank(idString)) {
                            Long id = Long.valueOf(idString);
                            SceneObject gameObject = gameObjectService.getGameObject(id);
                            if (gameObject != null && gameObject.getRoleType() == 1) {
                                NPC npc = new NPC();
                                BeanUtils.copyProperties(gameObject,npc);
                                gameScene.getNpcs().put(gameObject.getId(), npc);
                            }
                            if (gameObject != null && gameObject.getRoleType() == 2) {
                                Monster monster = new Monster();
                                BeanUtils.copyProperties(gameObject,monster);
                                gameScene.getMonsters().put(gameObject.getId(), monster);
                            }
                        }
                    });

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
