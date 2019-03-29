package com.wan37.gameserver.game.shop.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.shop.model.Shop;
import com.wan37.gameserver.game.shop.model.ShopExcelUtil;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.cache.ICacheManager;
import com.wan37.gameserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/4 11:28
 * @version 1.00
 * Description: 商店缓存管理
 */

@Service
@Slf4j
public class ShopManager  implements ICacheManager<Integer,Shop> {

    @Resource
    private ThingInfoService thingInfoService;



    private static Cache<Integer, Shop> shopCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "物品被移除, 原因是" + notification.getCause())
            ).build();




    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/shop.xlsx");
        ShopExcelUtil shopExcelUtil = new ShopExcelUtil(path);

        try {
            Map<Integer,Shop> shopMap = shopExcelUtil.getMap();
            for (Shop shop : shopMap.values()) {
                put(shop.getShopId(),shop);
            }
            log.info("商店配置表加载完毕");

        } catch (Exception e) {
            log.error("商店配置表加载失败");
            throw new RuntimeException(e);
        }

    }


    @Override
    public Shop get(Integer id) {
        Shop shop = shopCache.getIfPresent(id);
        // 如果货物字典并不为空，直接返回shop
//        if (!Strings.isNullOrEmpty(shop.getGoods())
//                && shop.getGoodsMap().isEmpty() )
//            return shop;

        // 如果货物列表未初始化，初始化货物
        String[] thingsIds = shop.getGoods().split(",");
        Arrays.stream(thingsIds)
                .map(Integer::valueOf)
                .forEach(thingId -> {
                    ThingInfo thingInfo = thingInfoService.getThingInfo(thingId);
                    shop.getGoodsMap().put(thingInfo.getId(), thingInfo);
                });
        log.debug("加载后的shop {}",shop);
        return shop;
    }

    @Override
    public void put(Integer shopId, Shop shop) {
        shopCache.put(shopId,shop);
    }
}
