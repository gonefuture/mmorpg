package com.wan37.gameserver.game.skills.service;

import com.wan37.gameserver.game.player.model.Buffer;
import com.wan37.gameserver.game.player.service.BufferService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.skills.model.ISkillEffect;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.game.skills.model.SkillType;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
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
@Slf4j
public class SkillEffect {

    @Resource
    private PetService petService;

    @Resource
    private BufferService bufferService;

    @Resource
    private NotificationManager notificationManager;


    private  Map<Integer, ISkillEffect>  skillEffectMap = new HashMap<>();


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


    {
        skillEffectMap.put(SkillType.ATTACK_SINGLE.getTypeId(),this::attackSingle);
        skillEffectMap.put(SkillType.CALL_PET.getTypeId(),this::callPet);
        skillEffectMap.put(SkillType.TAUNT.getTypeId(),this::taunt);
        skillEffectMap.put(SkillType.FRIENDLY.getTypeId(),this::friendly);
        // 群体攻击技能
        skillEffectMap.put(SkillType.ATTACK_MULTI.getTypeId(),this::attackSMulti);

    }

    /**
     *  群体攻击技能
     */
    private void attackSMulti(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHurt());
        target.setHp(target.getHp() + skill.getHeal());
        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0} 受到 {1} 群体攻击技能 {2}攻击，  hp减少{3},当前hp为 {4}\n",
                        target.getName(),initiator.getName(),skill.getName(),skill.getHurt(), target.getHp()));

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

        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0} 受到 {1} 技能 {2}攻击，  hp减少{3},当前hp为 {4}\n",
                        target.getName(),initiator.getName(),skill.getName(),skill.getHurt(), target.getHp()));
    }

    /**
     *  召唤兽类型的技能
     */
    private void callPet(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        petService.callPet(initiator,gameScene,skill.getCall());
    }


    /**
     *  嘲讽技能
     */
    private void taunt(Creature initiator, Creature target, GameScene gameScene, Skill skill) {

        // 将怪物目标设定为发起者
        target.setTarget(initiator);

        // 消耗mp和损伤目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() - skill.getHurt());
        target.setHp(target.getHp() + skill.getHeal());

        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0} 受到 {1} 技能 {2}攻击，  hp减少{3},当前hp为 {4}， {0}受到嘲讽\n",
                        target.getName(),initiator.getName(),skill.getName(),skill.getHurt(), target.getHp()));

    }


    /**
     *  对友方使用的技能
     */
    private void friendly(Creature initiator, Creature target, GameScene gameScene, Skill skill) {
        // 消耗mp和治疗目标hp
        initiator.setMp(initiator.getMp() - skill.getMpConsumption());
        target.setHp(target.getHp() + skill.getHeal());

        if(skill.getHeal() > 0) {
            notificationManager.notifyScene(gameScene, MessageFormat.format("{0} 受到 {1} 的治疗,hp增加了 {2}，当前hp是 {3}",
                    target.getName(),initiator.getName(),skill.getHeal(),target.getHp()));
        }

    }









}
