package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.mapper.TGameObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:41
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class GameObjectCacheMgr implements GameCacheManager<Long, TGameObject> {

    private static Cache<Long, TGameObject> gameObjectCache = CacheBuilder.newBuilder()
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
    private TGameObjectMapper tGameObjectMapper;

    @PostConstruct
    public void init() {
        List<TGameObject> tGameObjectList  = tGameObjectMapper.selectByExample(null);
        for (TGameObject tGameObject : tGameObjectList) {
            gameObjectCache.put(tGameObject.getId(), tGameObject);
        }

        log.info("游戏对象资源加载完毕");
    }


    @Override
    public TGameObject get(Long gameObjectId) {
        return gameObjectCache.getIfPresent(gameObjectId);
    }

    @Override
    public void put(Long gameObjectId, TGameObject value) {
        gameObjectCache.put(gameObjectId,value);
    }



    public Map<Long,TGameObject> list() {
        return gameObjectCache.asMap();
    }
}
