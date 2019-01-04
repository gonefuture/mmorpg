package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.model.User;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/28 15:44
 * @version 1.00
 * Description: 用户数据缓存
 */
@Component
public class UserCacheManger {





    private static Cache<ChannelHandlerContext, User> ctxUserCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)
            // 设置写缓存后，三小时过期
            .expireAfterWrite(3, TimeUnit.HOURS)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "用户被移除, 原因是" + notification.getCause())
            ).build();


    private static Cache<Long,ChannelHandlerContext> userIdCtxCache = CacheBuilder.newBuilder()
            // 设置写缓存后，三小时过期
            .expireAfterWrite(3, TimeUnit.HOURS)
            .build();

    /**
     *  key为channel id
     */

    public static User getUserByCtx(ChannelHandlerContext ctx) {
        return ctxUserCache.getIfPresent(ctx);
    }


    /**
     *  通过用户id获取一个缓存中的用户
     * @param userId 用户id
     * @return 用户
     */
    public static User getUserByUserId(Long userId) {
        return Optional.ofNullable(getCtxByUserId(userId))
                .map(UserCacheManger::getUserByCtx)
                .orElse(null);
    }


    /**
     *  将上下文和用户关联起来
     * @param ctx   上下文
     * @param user  用户
     */
    public static void putCtxUser(ChannelHandlerContext ctx, User user) {
        ChannelHandlerContext old = getCtxByUserId(user.getId());
        // 移除之前客户端登陆的用户

        Optional.ofNullable(old).ifPresent( o -> {
                    ctxUserCache.invalidate(o);
                    if (!old.equals(ctx)) {
                        NotificationManager.notifyByCtx(ctx,"你在另一个登陆，除非你在此从新登陆");
                    }
                }
                );

        ctxUserCache.put(ctx, user);
    }


    /**
     * 以Player id 为键，ChannelHandlerContext上下文为值
     */
    public static void putUserIdCtx(long userId, ChannelHandlerContext ctx) {
        userIdCtxCache.put(userId,ctx);
    }

    /**
     *    通过Player id 获取ChannelHandlerContext
     */
    public static ChannelHandlerContext getCtxByUserId(long userId) {

        return userIdCtxCache.getIfPresent(userId);
    }





    /**
     *  移除键为channel id， 值为 用户数据的缓存
     */
    public static void removeUserByChannelId(String channelId) {
        ctxUserCache.invalidate(channelId);
    }


    public static void removeUserIdCtx(Long userId) {
        userIdCtxCache.invalidate(userId);
    }
}
