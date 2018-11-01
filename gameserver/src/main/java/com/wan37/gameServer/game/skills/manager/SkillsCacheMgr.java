package com.wan37.gameServer.game.skills.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.model.SkillExcelUtil;
import com.wan37.gameServer.manager.cache.GameCacheManager;
import com.wan37.gameServer.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/9 10:01
 * @version 1.00
 * Description: mmorpg
 */

@Slf4j
@Component
public class SkillsCacheMgr implements GameCacheManager<Integer, Skill> {



    private static Cache<Integer, Skill> skillsCache = CacheBuilder.newBuilder()
            .recordStats()
            .removalListener(
                    notification -> log.debug(notification.getKey() + "技能被移除, 原因是" + notification.getCause())
            ).build();


    /**
     *     初始化技能
      */
    @PostConstruct
    public void init() {
        String path = FileUtil.getStringPath("gameData/skill.xlsx");
        SkillExcelUtil skillExcelUtil = new SkillExcelUtil(path);


        Map<Integer, Skill> skillMap = skillExcelUtil.getMap();

        for (Skill skill : skillMap.values()) {
            skillsCache.put(skill.getKey(), skill);
        }
        log.debug("技能 {}",skillMap);
        log.info("技能配置表加载");

    }


    @Override
    public Skill get(Integer skillId) {
        return skillsCache.getIfPresent(skillId);
    }

    @Override
    public void put(Integer skillId, Skill value) {
        skillsCache.put(skillId, value);
    }
}
