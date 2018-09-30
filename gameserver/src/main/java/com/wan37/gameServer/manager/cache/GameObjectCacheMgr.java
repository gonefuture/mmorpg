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

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/30 10:41
 * @version 1.00
 * Description: mmorpg
 */

@Component
@Slf4j
public class GameObjectCacheMgr implements GameCacheManager<String, TGameObject> {

    private static Cache<String, TGameObject> gameObjectCache = CacheBuilder.newBuilder()
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
            gameObjectCache.put(tGameObject.getId().toString(), tGameObject);
        }

        log.info("游戏对象资源加载完毕");
    }


    @Override
    public TGameObject get(String key) {
        return gameObjectCache.getIfPresent(key);
    }

    @Override
    public void put(String key, TGameObject value) {
        gameObjectCache.put(key,value);
    }
}
