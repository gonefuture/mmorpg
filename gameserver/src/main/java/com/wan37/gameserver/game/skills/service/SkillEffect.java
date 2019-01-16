package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.player.service.BufferService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.skills.model.ISkillEffect;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.game.skills.model.SkillType;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.model.Creature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2019/1/16 17:05
 * @version 1.00
 * Description: 技能作用
 */
@Component
public class SkillEffect {

    @Resource
    private PetService petService;

    @Resource
    private BufferService bufferService;

    @Resource
    private NotificationManager notificationManager;


    private  Map<Integer, ISkillEffect>  skillEffectMap = new HashMap<>();


    {
        skillEffectMap.put(SkillType.ATTACK_SINGLE.getTypeId(),this::attackSingle);
        skillEffectMap.put(SkillType.CALL_PET.getTypeId(),this::callPet);
        skillEffectMap.put(SkillType.TAUNT.getTypeId(),this::taunt);

    }


    /**
     *  根据技能类型触发技能效果
     * @param skillTypeId 技能类型id
     * @param initiator 施放者
     * @param target 施放目标
     * @param gameScene 场景
     * @param skill 技能
     */
    public void castSkill(Integer skillTypeId,
                          Creature initiator,
                          Creature target,
                          GameScene gameScene,
                          Skill skill) {
        Optional .ofNullable(skillEffectMap.get(skillTypeId)).ifPresent(s -> s.skillEffect(initiator,target,gameScene,skill));
    }



    /**
     *  施放单体攻击技能造成影响
     * @param initiator 施放者
     * @param target 施放目标
     * @param gameScene 场景
     * @param skill 技能
     */
    private  void attackSingle(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHurt());
        target.setHp(target.getHp() + skill.getHeal());


        // 如果技能触发的buffer不是0，则对敌方单体目标释放buffer
        if (!skill.getBuffer().equals(0)) {
            Buffer buffer = bufferService.getTBuffer(skill.getBuffer());
            // 如果buffer存在则启动buffer
            Optional.ofNullable(buffer).map(
                    (b) -> bufferService.startBuffer(target,b)
            );
        }
    }

    /**
     *  召唤兽类型的技能
     */
    private void callPet(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        petService.callPet(initiator,target,gameScene,skill.getCall());
    }


    /**
     *  嘲讽技能
     */
    private void taunt(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHurt());
        target.setHp(target.getHp() + skill.getHeal());

        // 讲怪物目标设定为发起者
        target.setTarget(initiator);

    }








}
