package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.entity.SceneActor;

import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TScene;

import com.wan37.mysql.pojo.mapper.TSceneMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 18:07
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SceneManager implements GameCacheManager<Integer, TScene> {


    // 缓存不过期
    private static Cache<Integer, TScene> sceneCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "was removed, cause is" + notification.getCause())
            ).build();


    private static Cache<String, List<SceneActor>> objectInScene = CacheBuilder.newBuilder()
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



    @PostConstruct
    private void init() {
        List<TScene> tSceneList = tSceneMapper.selectByExample(null);
        for (TScene tScene: tSceneList) {
            sceneCache.put(tScene.getId(), tScene );
        }

        log.info("场景资源加载进缓存完毕");
    }


    @Override
    public TScene get(Integer key) {
        return sceneCache.getIfPresent(key);
    }

    @Override
    public void put(Integer key, TScene value) {
        throw new UnsupportedOperationException();
    }



    public List<SceneActor> getObjectsInScene(Long sceneId) {
        return objectInScene.getIfPresent("ObjectsInScene:"+sceneId);
    }

    public void  putObjectToScene(Long sceneId,SceneActor sceneActor) {
        List<SceneActor> sceneActorList = getObjectsInScene(sceneId);
        sceneActorList.add(sceneActor);
        objectInScene.put("ObjectsInScene:"+sceneId, sceneActorList);
    }
}
