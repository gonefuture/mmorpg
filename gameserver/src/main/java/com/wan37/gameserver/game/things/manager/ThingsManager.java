package com.wan37.gameserver.game.things.manager;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.things.model.ThingInfo;
import com.wan37.gameserver.game.things.model.ThingInfoExcelUtil;
import com.wan37.gameserver.game.things.service.ThingInfoService;
import com.wan37.gameserver.manager.cache.ICacheManager;
import com.wan37.gameserver.util.FileUtil;


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
public class ThingsManager implements ICacheManager<Integer, ThingInfo> {

    @Resource
    private ThingInfoService thingInfoService;

    private static Cache<Integer, ThingInfo> thingsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "物品被移除, 原因是" + notification.getCause())
            ).build();


    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/things.xlsx");
        ThingInfoExcelUtil thingInfoExcelUtil = new ThingInfoExcelUtil(path);

        try {
            Map<Integer, ThingInfo> thingsMap = thingInfoExcelUtil.getMap();
            for (ThingInfo thingInfoExcel : thingsMap.values()) {
                ThingInfo thingInfo = new ThingInfo();
                BeanUtils.copyProperties(thingInfoExcel, thingInfo);

                put(thingInfoExcel.getId(), thingInfo);
            }
            log.info("物品配置表加载完毕");

        } catch (Exception e) {
            log.error("物品配置表加载失败");
            throw new RuntimeException(e);
        }

    }


    @Override
    public ThingInfo get(Integer id) {
        ThingInfo thingInfo = thingsCache.getIfPresent(id);
        // 如果属性集合为空，加载物品属性
        if (thingInfo != null
                && !Strings.isNullOrEmpty(thingInfo.getRoleProperties())
                && thingInfo.getThingRoleProperty().isEmpty()) {
            thingInfoService.loadThingsProperties(thingInfo);
        }
        return thingInfo;
    }

    @Override
    public void put(Integer id, ThingInfo thingInfo) {
        thingsCache.put(id, thingInfo);
    }
}
