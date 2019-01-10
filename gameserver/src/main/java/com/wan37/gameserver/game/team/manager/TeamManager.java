package com.wan37.gameserver.game.team.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.team.model.Team;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/17 12:26
 * @version 1.00
 * Description: 队伍管理
 */

@Component
public class TeamManager {


    // 缓存不过期
    private static Cache<String, Team> teamCache = CacheBuilder.newBuilder()
            // 设置并发级别，最多8个线程同时写
            .concurrencyLevel(10)
            // 设置缓存容器的初始容量为100
            .initialCapacity(100)
            .maximumSize(5000)
            .recordStats()
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "队伍被移除, 原因是" + notification.getCause())
            ).build();



    public static Team getTeam(String id) {
        // 如果队伍id是默认的空，则返回null
       if (id.isEmpty())
            return null;
        return teamCache.getIfPresent(id);
    }

    public static void putTeam(String teamId, Team team) {
        teamCache.put(teamId,team);
    }


    /**
     *   组队请求的缓存,key是被邀请玩家的id,value是发起邀请的玩家id
     */
    private static Cache<Long,Long> teamRequestCache = CacheBuilder.newBuilder()
            // 设置三十秒后移除组队请求
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "组队请求被移除, 原因是" + notification.getCause())
            ).build();

    public static Long getTeamRequest(Long inviteeId) {
        return teamRequestCache.getIfPresent(inviteeId);
    }


    public static void putTeamRequest(Long inviteeId, Long inviter){
        teamRequestCache.put(inviteeId,inviter);

    }




}
