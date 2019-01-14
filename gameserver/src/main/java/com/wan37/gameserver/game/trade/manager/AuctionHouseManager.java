package com.wan37.gameserver.game.trade.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wan37.gameserver.game.trade.dao.AuctionItemDao;
import com.wan37.gameserver.game.trade.model.AuctionItem;
import com.wan37.gameserver.game.trade.service.AuctionHouseService;
import com.wan37.gameserver.manager.task.TimedTaskManager;
import com.wan37.gameserver.manager.task.WorkThreadPool;
import com.wan37.mysql.pojo.entity.TAuctionItem;
import com.wan37.mysql.pojo.mapper.TAuctionItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
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

    @Resource
    private AuctionHouseService auctionHouseService;




    @PostConstruct
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
        log.debug("拍卖行数据从数据库加载完毕");
        auctionHouseListener();
        log.debug("拍卖行到时监听开始");
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



    @Resource
    private AuctionItemDao auctionItemDao;


    /**
     *  向缓存和数据库中添加拍卖品
     */
    public  void putAuctionItem(AuctionItem auctionItem) {
        auctionItem.setBidders("");
        CompletableFuture<Integer>  future = CompletableFuture.supplyAsync(
                () -> {
                    auctionItemDao.insertAndReturnKey(auctionItem);
                    log.debug("===== auctionItem {}",auctionItem);
                    auctionItemCache.put(auctionItem.getAuctionId(),auctionItem);
                    return auctionItem.getAuctionId();
            }, WorkThreadPool.threadPool
        ).exceptionally(
                e -> {
                    log.debug("持久化拍卖品出现异常 {}",e);
                    return 0;
                }
        );
    }



    public void updateAuctionItem(AuctionItem auctionItem) {
        WorkThreadPool.threadPool.execute(() -> tAuctionItemMapper.updateByPrimaryKey(auctionItem));
    }

    /**
     *  从数据库和缓存中中移除拍卖品
     */
    public void removeAuctionItem(Integer auctionId) {
        auctionItemCache.invalidate(auctionId);
        WorkThreadPool.threadPool.execute(() -> tAuctionItemMapper.deleteByPrimaryKey(auctionId));
    }

    /**
     *  循环检测拍卖是否过时
     */
    private void auctionHouseListener() {
        TimedTaskManager.scheduleAtFixedRate(5000,500,
                () -> {
                    try {
                        auctionItemCache.asMap().values()
                                .forEach(
                                        item -> {
                                            log.debug("拍卖品{}",item);
                                            // 如果拍卖品被拍卖超过一天，结束拍卖
                                            if (System.currentTimeMillis() - item.getPushTime().getTime() > Duration.ofMinutes(1).toMillis()) {
                                                auctionHouseService.finishAuction(item);
                                            }
                                        }
                                );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }


}
