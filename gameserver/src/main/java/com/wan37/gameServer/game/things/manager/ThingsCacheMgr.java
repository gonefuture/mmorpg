package com.wan37.gameServer.game.things.manager;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.things.model.Things;
import com.wan37.gameServer.game.things.model.ThingsExcelUtil;
import com.wan37.gameServer.game.things.service.ThingsService;
import com.wan37.gameServer.manager.cache.GameCacheManager;
import com.wan37.gameServer.util.FileUtil;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource
    private ThingsService thingsService;

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
        Things thing = thingsCache.getIfPresent(id);
        // 如果属性集合为空，加载物品属性
        if (thing != null
                && !Strings.isNullOrEmpty(thing.getRoleProperties())
                && thing.getThingRoleProperty().isEmpty()) {
            thingsService.loadThingsProperties(thing);
        }
        return  thing;
    }

    @Override
    public void put(Integer id, Things things) {
        thingsCache.put(id,things);
    }
}
