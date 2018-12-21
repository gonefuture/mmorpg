package com.wan37.gameServer.game.gameRole.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.BufferExcelUtil;
import com.wan37.gameServer.game.gameRole.model.RoleClass;
import com.wan37.gameServer.game.gameRole.model.RoleClassExcelUtil;
import com.wan37.gameServer.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.management.relation.Role;
import java.util.Arrays;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/21 17:00
 * @version 1.00
 * Description: 职业配置数据加载
 */

@Slf4j
@Component
public class RoleClassManager {


    private static Cache<Integer, RoleClass> roleClassCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "职业被移除, 原因是" + notification.getCause())
            ).build();


    @PostConstruct
    private void init() {

        String path = FileUtil.getStringPath("gameData/gameRole.model.RoleClass.xlsx");
        RoleClassExcelUtil roleClassExcelUtil = new RoleClassExcelUtil(path);
        Map<Integer, RoleClass> roleClassMap = roleClassExcelUtil.getMap();


        roleClassMap.values().forEach(
                roleClass -> roleClassCache.put(roleClass.getId(),roleClass)
        );

        log.info("角色职业数据加载完毕");
    }



    public static RoleClass getRoleClass(Integer roleClassId) {
        return roleClassCache.getIfPresent(roleClassId);
    }


}
