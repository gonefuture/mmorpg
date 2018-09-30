package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.entity.User;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.nio.channels.Channel;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 15:44
 * @version 1.00
 * Description: mmorpg
 */
@Component
public class UserCacheMgr implements GameCacheManager<String, User>{

    private static Cache<String, User> channelUserCache = CacheBuilder.newBuilder()
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


    private static Cache<Long,ChannelHandlerContext> idSUserCache = CacheBuilder.newBuilder().build();

    /**
     *  key为channel id
     */
    @Override
    public User get(String key) {
        return channelUserCache.getIfPresent(key);
    }


    /**
     *  key为channel id
     */
    @Override
    public void put(String key, User value) {
        channelUserCache.put(key, value);
    }

    /**
     *    通过Player id 获取ChannelHandlerContext
     */
    public ChannelHandlerContext getCtxById(long userId) {
        return idSUserCache.getIfPresent(userId);
    }


    /**
     * 以Player id 为键，ChannelHandlerContext上下文为值
     */
    public void saveCtx(long userId, ChannelHandlerContext ctx) {
        idSUserCache.put(userId,ctx);
    }
}
