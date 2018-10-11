package com.wan37.gameServer.manager.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TSkill;
import com.wan37.mysql.pojo.mapper.TSkillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/9 10:01
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SkillsCacheMgr implements GameCacheManager<Integer, TSkill>{

    @Resource
    private TSkillMapper tSkillMapper;


    private static Cache<Integer, TSkill> skillsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "技能被移除, 原因是" + notification.getCause())
            ).build();


    /**
     *     初始化技能
      */
    @PostConstruct
    public void init() {
        List<TSkill> tSkillList = tSkillMapper.selectByExample(null);

        for (TSkill skill: tSkillList) {
            skillsCache.put(skill.getId(), skill);
        }


    }


    @Override
    public TSkill get(Integer skillId) {
        return skillsCache.getIfPresent(skillId);
    }

    @Override
    public void put(Integer skillId, TSkill value) {
        skillsCache.put(skillId, value);
    }
}
