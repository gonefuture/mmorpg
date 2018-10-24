package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


import com.wan37.gameServer.game.gameRole.modle.Player;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 14:32
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Component
public class PlayerCacheMgr implements GameCacheManager<String, Player> {

    private static Cache<String, Player> gamePlayerCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)

            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "玩家被移除，原因是" + notification.getCause())
            ).build();

    private static Cache<Long, ChannelHandlerContext> playerCtxCache = CacheBuilder.newBuilder().build();

    /**
     *  键为channel id
     */
    @Override
    public Player get(String channelId) {
        return gamePlayerCache.getIfPresent(channelId);
    }


    /**
     *  值为玩家
     */
    @Override
    public void put(String channelId, Player player) {
        gamePlayerCache.put(channelId,player);
    }


    /**
     *  通过 channel Id 清除玩家信息
     */
    public void  removePlayerByChannelId(String channelId) {
        gamePlayerCache.invalidate(channelId);
    }


    /**
     * 玩家id来保存ChannelHandlerContext
     */
    public void savePlayerCtx(long playerId, ChannelHandlerContext cxt) {
        playerCtxCache.put(playerId, cxt);
    }


    /**
     *  根据玩家id获取ChannelHandlerContext
     * @param playerId 玩家id
     */
    public ChannelHandlerContext getCxtByPlayerId(long playerId) {
        return playerCtxCache.getIfPresent(playerId);
    }


    public  void removePlayerCxt(long playerId) {
        playerCtxCache.invalidate(playerId);
    }

}
