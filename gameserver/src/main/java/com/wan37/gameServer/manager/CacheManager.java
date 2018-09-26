package com.wan37.gameServer.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.wan37.gameServer.entity.map.Position;
import com.wan37.gameServer.entity.role.Adventurer;
import com.wan37.gameServer.entity.role.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/26 14:34
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Component
public class CacheManager {

    public static Cache<Object, Object> gameCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(8)
            // 设置写缓存后，三小时过期
            .expireAfterWrite(3, TimeUnit.HOURS)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(200)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "was removed, cause is" + notification.getCause())
            ).build();


    private CacheManager(){
        Random r = new Random();
        // 创新新的冒险者
        Role role = new Adventurer(r.nextLong(),"贪婪的冒险者",9999,9999);

        Position position = new Position(0,0);
        role.setPosition(position);
        gameCache.put("1", role);
    }

}
