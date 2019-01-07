package com.wan37.gameServer.game.player.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 14:32
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Component
public class PlayerCacheMgr  {

    private static Cache<ChannelHandlerContext, Player> ctxPlayerCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)

            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "玩家被移除，原因是" + notification.getCause())
            ).build();

    private static Cache<Long, ChannelHandlerContext> IdCtxCache = CacheBuilder.newBuilder().build();

    /**
     *  键为channel id
     */

    public Player getPlayerByCtx(ChannelHandlerContext ctx) {
        return ctxPlayerCache.getIfPresent(ctx);
    }


    /**
     *  值为玩家
     */
    public void putCtxPlayer(ChannelHandlerContext ctx, Player player) {

        ChannelHandlerContext old = getCxtByPlayerId(player.getId());
        Optional.ofNullable(old).ifPresent(o -> {
                    ctxPlayerCache.invalidate(o);
                    if (!old.equals(ctx)) {
                        NotificationManager.notifyByCtx(old,"角色在其他敌方登陆，你已不能进行正常角色操作，除非重新登陆用户加载角色");
                    }
                }
        );

        ctxPlayerCache.put(ctx,player);
    }


    /**
     *  通过 channel Id 清除玩家信息
     */
    public void  removePlayerByChannelId(String channelId) {
        ctxPlayerCache.invalidate(channelId);
    }


    /**
     * 玩家id来保存ChannelHandlerContext
     */
    public void savePlayerCtx(long playerId, ChannelHandlerContext cxt) {
        IdCtxCache.put(playerId, cxt);
    }


    /**
     *  根据玩家id获取ChannelHandlerContext
     * @param playerId 玩家id
     */
    public ChannelHandlerContext getCxtByPlayerId(long playerId) {
        return IdCtxCache.getIfPresent(playerId);
    }


    public  void removePlayerCxt(long playerId) {
        IdCtxCache.invalidate(playerId);
    }


    public Map<ChannelHandlerContext, Player> getAllPlayerCache() {
        return ctxPlayerCache.asMap();
    }

}
