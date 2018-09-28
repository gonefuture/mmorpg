package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.Player;
import com.wan37.mysql.pojo.entity.Scene;
import com.wan37.mysql.pojo.mapper.SceneMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/27 18:07
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SceneManager implements GameCacheManager<Integer, Scene> {

    // 缓存不过期
    private static Cache<String, Scene> gameCache = CacheBuilder.newBuilder()
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
    private SceneMapper sceneMapper;


    @PostConstruct
    private void init() {
        List<Scene> sceneList = sceneMapper.selectByExample(null);
        for (Scene scene: sceneList) {
            gameCache.put("scene:"+scene.getId(),scene);
        }
        log.info("场景资源加载进缓存完毕");
    }


    @Override
    public Scene get(Integer key) {
        return gameCache.getIfPresent("scene:"+key);
    }

    @Override
    public void put(Integer key, Scene value) {
        throw new UnsupportedOperationException();
    }
}
