package com.wan37.gameserver.game.user.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.manager.cache.ICacheManager;
import com.wan37.mysql.pojo.entity.TPlayer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 15:03
 * @version 1.00
 * Description: 角色列表缓存
 */
@Slf4j
@Component
public class PlayerListCacheMgr implements ICacheManager<Long,List<TPlayer>> {

    private static Cache<Long, List<TPlayer>> playerListCache = CacheBuilder.newBuilder()
            // 设置写缓存后，三小时过期
            .expireAfterWrite(3, TimeUnit.HOURS)
            .removalListener(
                    notification -> log.info(notification.getKey() + "被移除, 原因是" + notification.getCause())
            ).build();



    @Override
    public List<TPlayer> get(Long userId) {
        return playerListCache.getIfPresent(userId);
    }

    @Override
    public void put(Long userId, List<TPlayer> playerList) {
        playerListCache.put(userId, playerList);
    }
}
