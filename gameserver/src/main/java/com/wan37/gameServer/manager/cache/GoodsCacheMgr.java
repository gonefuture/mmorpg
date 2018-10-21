package com.wan37.gameServer.manager.cache;
/*
 *  @author : 钱伟健 gonefuture@qq.com
 *  @version : 2018/10/21 21:23.
 *  说明：
 */

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.TGoods;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre> </pre>
 */
@Slf4j
public class GoodsCacheMgr  implements  GameCacheManager<Long,TGoods>{
    private static Cache<Long, TGoods> goodsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "物品被移除, 原因是" + notification.getCause())
            ).build();


    @Override
    public TGoods get(Long id) {
        return goodsCache.getIfPresent(id);
    }

    @Override
    public void put(Long id, TGoods goods) {
        goodsCache.put(id,goods);
    }
}
