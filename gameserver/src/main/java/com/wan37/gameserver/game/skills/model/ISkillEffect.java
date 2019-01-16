package com.wan37.gameserver.game.skills.model;

import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.model.Creature;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/16 17:13
 * @version 1.00
 * Description: 技能生效的抽象
 */

@FunctionalInterface
public interface ISkillEffect {

    /**
     *  施放技能造成影响
     * @param initiator 施放者
     * @param target 施放目标
     * @param gameScene 场景
     * @param skill 技能
     */
    void skillEffect(Creature initiator, Creature target, GameScene gameScene, Skill skill);
}
