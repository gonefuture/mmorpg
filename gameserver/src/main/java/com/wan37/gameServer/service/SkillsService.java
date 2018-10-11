package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.SkillsCacheMgr;
import com.wan37.mysql.pojo.entity.TSkill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/10 12:05
 * @version 1.00
 * Description: mmorpg
 */
@Service
public class SkillsService {

    @Resource
    private SkillsCacheMgr skillsCacheMgr;

    /**
     *  检查游戏技能的冷却时间是否允许发动技能
     */
    public boolean checkCD(Player player, TSkill tSkill) {
       if (player != null && tSkill != null) {
           TSkill playerSkill = player.getSkillMap().get(tSkill.getId());
           // 如果没有使用这个技能，立刻使用并计算cd
           if (playerSkill == null) {
               startSkill(player,tSkill);
               return true;
           }

           long now = System.currentTimeMillis();
           long targetTime = playerSkill.getActivetime() + tSkill.getCd()*1000;
           // 技能冷却时间过去了
           if (targetTime <= now) {
               startSkill(player,tSkill);
               return true;
           } else {
               return false;
           }
       } else {
           return  false;
       }

    }


    public void startSkill(Player player, TSkill tSkill) {
        tSkill.setActivetime(System.currentTimeMillis());
        player.getSkillMap().put(tSkill.getId(),tSkill);
    }


}
