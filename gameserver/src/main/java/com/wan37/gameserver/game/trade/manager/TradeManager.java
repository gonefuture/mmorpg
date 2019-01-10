package com.wan37.gameserver.game.trade.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.trade.model.TradeBoard;

import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/18 17:09
 * @version 1.00
 * Description: 交易管理
 */
public class TradeManager {


    /** 交易栏缓存 */
    private static Cache<Long, TradeBoard> tradeBoardCache = CacheBuilder.newBuilder()
            // 设置360秒后交易栏被移除
            .expireAfterWrite(360, TimeUnit.SECONDS)
            .removalListener(
                    notification -> System.out.println(notification.getKey() + "交易栏被移除, 原因是" + notification.getCause())
            ).build();


    public static TradeBoard getTradeBoard(Long playerId) {
        return tradeBoardCache.getIfPresent(playerId);
    }


    public static void putTradeBoard(Long playerId, TradeBoard tradeBoard) {
        tradeBoardCache.put(playerId,tradeBoard);
    }






}
