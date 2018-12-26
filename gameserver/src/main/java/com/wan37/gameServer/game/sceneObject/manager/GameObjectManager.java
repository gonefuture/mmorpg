package com.wan37.gameServer.game.sceneObject.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.sceneObject.model.SceneObjectExcelUtil;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;

import com.wan37.gameServer.manager.cache.ICacheManager;
import com.wan37.gameServer.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:41
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class GameObjectManager implements ICacheManager<Long, SceneObject> {

    private static Cache<Long, SceneObject> gameObjectCache = CacheBuilder.newBuilder()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "游戏对象被移除，原因是" + notification.getCause())
            ).build();


    @PostConstruct
    public void init() {
        String path = FileUtil.getStringPath("gameData/sceneObject.xlsx");
        SceneObjectExcelUtil sceneObjectExcelUtil = new SceneObjectExcelUtil(path);
        Map<Integer,SceneObject> sceneObjectMap = sceneObjectExcelUtil.getMap();

        for (SceneObject sceneObject : sceneObjectMap.values()) {
            gameObjectCache.put(sceneObject.getId(), sceneObject);
        }

        log.info("游戏对象资源加载完毕");
    }


    @Override
    public SceneObject get(Long gameObjectId) {
        return gameObjectCache.getIfPresent(gameObjectId);
    }

    @Override
    public void put(Long gameObjectId, SceneObject value) {
        gameObjectCache.put(gameObjectId,value);
    }



    public Map<Long,SceneObject> list() {
        return gameObjectCache.asMap();
    }
}
