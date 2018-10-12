package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.entity.Monster;
import com.wan37.gameServer.entity.NPC;
import com.wan37.gameServer.service.GameObjectService;
import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TScene;
import com.wan37.mysql.pojo.mapper.TSceneMapper;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    private TSceneMapper tSceneMapper;

    @Resource
    private GameObjectService  gameObjectService;


    @PostConstruct
    private void init() {
        List<TScene> tSceneList = tSceneMapper.selectByExample(null);
        for (TScene tScene: tSceneList) {
            GameScene gameScene = new GameScene();
            BeanUtils.copyProperties(tScene,gameScene);

            String[] ids = tScene.getGameObjectIds().split(",");

            Arrays.stream(ids).forEach(
                    (idString) ->{
                        if (Strings.isNotBlank(idString)) {
                            Long id = Long.valueOf(idString);
                            TGameObject tGameObject = gameObjectService.getGameObject(id);
                            if (tGameObject != null && tGameObject.getRoleType() == 1) {
                                NPC npc = new NPC();
                                BeanUtils.copyProperties(tGameObject,npc);
                                gameScene.getNpcs().put(tGameObject.getId(), npc);
                            }
                            if (tGameObject != null && tGameObject.getRoleType() == 2) {
                                Monster monster = new Monster();
                                BeanUtils.copyProperties(tGameObject,monster);
                                gameScene.getMonsters().put(tGameObject.getId(), monster);
                            }
                        }
                    });

            sceneCache.put(tScene.getId(), gameScene);
        }

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
