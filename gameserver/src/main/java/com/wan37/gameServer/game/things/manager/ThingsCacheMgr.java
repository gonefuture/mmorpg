package com.wan37.gameServer.game.things.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.things.modle.Things;
import com.wan37.gameServer.game.things.modle.ThingsExcelUtil;
import com.wan37.gameServer.manager.cache.GameCacheManager;
import com.wan37.gameServer.util.FileUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/23 15:56
 * @version 1.00
 * Description: mmorpg
 */
@Slf4j
@Component
public class ThingsCacheMgr  implements GameCacheManager<Integer,Things> {

    private static Cache<Integer, Things> thingsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "物品被移除, 原因是" + notification.getCause())
            ).build();


    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/things.xlsx");
        ThingsExcelUtil thingsExcelUtil = new ThingsExcelUtil(path);

        try {
            Map<Integer,Things> thingsMap = thingsExcelUtil.getMap();
            for (Things thingsExcel : thingsMap.values()) {
                Things things= new Things();
                BeanUtils.copyProperties(thingsExcel,things);
                put(thingsExcel.getId(),things);
            }
            log.debug("物品配置配置表数据 {}",thingsMap);
            log.info("物品配置表加载完毕");

        } catch (Exception e) {
            log.error("物品配置表加载失败");
            throw new RuntimeException(e);
        }

    }


    @Override
    public Things get(Integer id) {
        return thingsCache.getIfPresent(id);
    }

    @Override
    public void put(Integer id, Things things) {
        thingsCache.put(id,things);
    }
}
