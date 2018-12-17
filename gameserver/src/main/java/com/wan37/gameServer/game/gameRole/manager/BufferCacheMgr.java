package com.wan37.gameServer.game.gameRole.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.BufferExcelUtil;
import com.wan37.gameServer.manager.cache.ICacheManager;
import com.wan37.gameServer.util.FileUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 17:59
 * @version 1.00
 * Description: Buffer缓存管理
 */

@Slf4j
@Component
public class BufferCacheMgr implements ICacheManager<Integer, Buffer> {


    private static Cache<Integer, Buffer> bufferCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "技能被移除, 原因是" + notification.getCause())
            ).build();


    @PostConstruct
    private void init() {

        String path = FileUtil.getStringPath("gameData/buffer.xlsx");
        BufferExcelUtil bufferExcelUtil = new BufferExcelUtil(path);
        Map<Integer,Buffer>  bufferMap = bufferExcelUtil.getMap();
        for (Buffer buffer : bufferMap.values()) {
            put(buffer.getId(), buffer);
        }

        log.info("buffer数据加载完毕");
    }


    @Override
    public Buffer get(Integer bufferId) {
        return bufferCache.getIfPresent(bufferId);
    }

    @Override
    public void put(Integer bufferId, Buffer value) {
        bufferCache.put(bufferId,value);
    }
}
