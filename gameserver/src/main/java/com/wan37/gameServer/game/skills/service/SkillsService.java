package com.wan37.gameServer.game.skills.service;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.skills.manager.SkillsCacheMgr;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.model.Creature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public boolean checkCD(Player player, Skill skill) {
       if (player != null && skill != null) {
           Skill playerSkill = player.getSkillMap().get(skill.getId());
           // 如果没有使用这个技能，立刻使用并计算cd
           if (playerSkill == null) {
               startSkill(player,skill);
               return true;
           }

           long now = System.currentTimeMillis();
           long targetTime = playerSkill.getActivetime() + skill.getCd()*1000;
           // 技能冷却时间过去了
           if (targetTime <= now) {
               startSkill(player,skill);
               return true;
           } else {
               return false;
           }
       } else {
           return  false;
       }

    }


    public void startSkill(Player player, Skill skill) {
        skill.setActivetime(System.currentTimeMillis());
        player.getSkillMap().put(skill.getId(),skill);
    }

    /**
     *  通过技能id获取技能
     * @param skillId 技能id
     * @return 技能
     */
    public Skill getSkill(Integer skillId) {
        return skillsCacheMgr.get(skillId);
    }


    public boolean useSkill(Creature creature, ) {

    }





}
