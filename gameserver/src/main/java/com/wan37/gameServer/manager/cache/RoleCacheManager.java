package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.GameRole;
import com.wan37.mysql.pojo.entity.Player;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 15:44
 * @version 1.00
 * Description: mmorpg
 */
@Component
public class RoleCacheManager implements GameCacheManager<Long, GameRole>{

    private static Cache<Long, GameRole> gameCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)
            // 设置写缓存后，三小时过期
            .expireAfterWrite(3, TimeUnit.HOURS)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "was removed, cause is" + notification.getCause())
            ).build();

    @Override
    public GameRole get(Long key) {
        return gameCache.getIfPresent(key);
    }

    @Override
    public void put(Long key, GameRole value) {
        gameCache.put(key, value);
    }
}
