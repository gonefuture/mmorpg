package com.wan37.gameserver.game.roleProperty.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameserver.game.roleProperty.model.RoleProperty;

import com.wan37.gameserver.game.roleProperty.model.RolePropertyExcelUtil;

import com.wan37.gameserver.manager.cache.ICacheManager;
import com.wan37.gameserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/24 17:03
 * @version 1.00
 * Description: 角色属性管理
 */

@Component
@Slf4j
public class RolePropertyManager  implements ICacheManager<Integer,RoleProperty> {


    private static Cache<Integer,RoleProperty> rolePropertyCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "角色属性被移除, 原因是" + notification.getCause())
            ).build();



    @PostConstruct
    private void init() {
        String path = FileUtil.getStringPath("gameData/roleProperty.xlsx");
        RolePropertyExcelUtil rolePropertyExcelUtil = new RolePropertyExcelUtil(path);
        Map<Integer,RoleProperty> rolePropertyMap = rolePropertyExcelUtil.getMap();
        for (RoleProperty roleProperty : rolePropertyMap.values()) {
            put(roleProperty.getKey(),roleProperty);
        }
        log.info("角色属性配置表加载完毕");
    }


    @Override
    public RoleProperty get(Integer id) {
        return rolePropertyCache.getIfPresent(id);
    }

    @Override
    public void put(Integer id, RoleProperty value) {
        rolePropertyCache.put(id,value);
    }
}
