package com.wan37.gameServer.game.gameRole.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.gameRole.model.Bags;
import com.wan37.gameServer.manager.cache.GameCacheManager;
import com.wan37.mysql.pojo.entity.TThings;
import com.wan37.mysql.pojo.entity.TThingsExample;
import com.wan37.mysql.pojo.mapper.TThingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/25 16:58
 * @version 1.00
 * Description: mmorpg
 */
@Component
@Slf4j
public class BagsManager implements GameCacheManager<Long,Bags> {

    private static Cache<Long,Bags> bagsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "背包被移除, 原因是" + notification.getCause())
            ).build();


    @Resource
    private TThingsMapper tThingsMapper;



    @Override
    public Bags get(Long playerId) {
        return bagsCache.getIfPresent(playerId);
    }

    @Override
    public void put(Long id, Bags value) {
        bagsCache.put(id,value);
    }
}
