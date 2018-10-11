package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.TBuffer;
import com.wan37.mysql.pojo.entity.TSkill;
import com.wan37.mysql.pojo.mapper.TBufferMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 17:59
 * @version 1.00
 * Description: Buffer缓存管理
 */

@Slf4j
@Component
public class BufferCacheMgr implements GameCacheManager<Integer, TBuffer>{

    @Resource
    private TBufferMapper tBufferMapper;

    private static Cache<Integer, TBuffer> bufferCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "技能被移除, 原因是" + notification.getCause())
            ).build();


    @PostConstruct
    private void init() {
        List<TBuffer> tBufferList = tBufferMapper.selectByExample(null);
        for (TBuffer tBuffer : tBufferList) {
            put(tBuffer.getId(), tBuffer);
        }
        log.info("buffer数据加载完毕");
    }


    @Override
    public TBuffer get(Integer bufferId) {
        return bufferCache.getIfPresent(bufferId);
    }

    @Override
    public void put(Integer bufferId, TBuffer value) {
        bufferCache.put(bufferId,value);
    }
}
