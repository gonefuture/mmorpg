package com.wan37.gameserver.game.trade.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wan37.gameserver.game.trade.model.AuctionItem;
import com.wan37.gameserver.manager.task.WorkThreadPool;
import com.wan37.mysql.pojo.entity.TAuctionItem;
import com.wan37.mysql.pojo.mapper.TAuctionItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/9 18:13
 * @version 1.00
 * Description: 拍卖行管理
 */

@Slf4j
@Component
public class AuctionHouseManager {




    private static Cache<Integer, AuctionItem> auctionItemCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "拍卖品被移除, 原因是" + notification.getCause())
            ).build();



    @Resource
    private TAuctionItemMapper tAuctionItemMapper;





    private void initAuctionHouse() {
        WorkThreadPool.threadPool.execute(() -> {
            List<TAuctionItem> tAuctionItemList = tAuctionItemMapper.selectByExample(null);
            tAuctionItemList.forEach(
                    tAuctionItem -> {
                        AuctionItem auctionItem = new AuctionItem();
                        BeanUtils.copyProperties(tAuctionItem,auctionItem);
                        auctionItemCache.put(auctionItem.getAuctionId(),auctionItem);
                    }
            );
        });
    }


    /**
        所有拍卖品
     */
    public static Map<Integer,AuctionItem> getAllAuctionItem() {
        return auctionItemCache.asMap();
    }

    /**
     *  从缓存获取拍卖品
     */
    public static AuctionItem  getAuctionItem(Integer auctionId) {
        return auctionItemCache.getIfPresent(auctionId);
    }


    /**
     *  向缓存和数据库中添加拍卖品
     */
    public  void putAuctionItem(AuctionItem auctionItem) {
        CompletableFuture<Integer>  future = CompletableFuture.supplyAsync(
                () -> {
            tAuctionItemMapper.insert(auctionItem);
            return auctionItem.getAuctionId();
            }, WorkThreadPool.threadPool
        );

        future.thenRunAsync(() -> {
            log.debug("===== auctionItem {}",auctionItem);
            auctionItemCache.put(auctionItem.getAuctionId(),auctionItem);
        }, WorkThreadPool.threadPool);

    }



    /**
     *  从数据库和缓存中中移除拍卖品
     */
    public void removeAuctionItem(Integer auctionId) {
        auctionItemCache.invalidate(auctionId);
        WorkThreadPool.threadPool.execute(() -> tAuctionItemMapper.deleteByPrimaryKey(auctionId));
    }



}
