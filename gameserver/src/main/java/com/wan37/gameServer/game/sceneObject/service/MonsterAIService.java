package com.wan37.gameServer.game.sceneObject.service;

import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameInstance.service.InstanceService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/12 14:22
 * @version 1.00
 * Description: 怪物AI服务
 */

@Slf4j
@Service
public class MonsterAIService {


    @Resource
    private NotificationManager notificationManager;

    @Resource
    private CombatService combatService;

    @Resource
    private SkillsService skillsService;



    /**
     *  怪物进行攻击
     * @param monster 怪物
     * @param target 目标
     */
    public void monsterAttack(Monster monster, Creature target) {
        Integer attack = monster.getAttack();
        target.setHp(target.getHp() - attack);

        if (target instanceof Player) {
            notificationManager.notifyPlayer((Player) target,
                    MessageFormat.format("{0}在攻击你， 对你造成了{1}点伤害，你当前的hp为 {2} \n",
                    monster.getName(), monster.getAttack(),target.getHp()));
            combatService.isPlayerDead((Player) target,monster);
        }
    }


    /**
     *      怪物使用技能，从怪物拥有的技能随机触发
     * @param monster 怪物
     * @param target 怪物攻击的目标
     */
    public void monsterUseSkill(Monster monster, Creature target,GameScene gameScene) {

        Arrays.stream(monster.getSkills().split(","))
                .map(Integer::valueOf).parallel() // 随机返回一个技能id
                .findAny().ifPresent(
                skillId -> { // 如果技能存在，则释放技能
                    Skill skill = skillsService.getSkill(skillId);
                    if (skillsService.useSkill(monster,target,gameScene,skill)) {
                        notificationManager.notifyScene(gameScene,
                                MessageFormat.format("{0} 对你{1}用技能 {2}， 对你造成了{3}点伤害，你当前的hp为 {4}\n",
                                        monster.getName(), target.getName(),skill.getName(),skill.getHpLose(),target.getHp()));
                    }
                }
        );
    }


    /**
     *  怪物（包括玩家宠物）自动攻击
     * @param target 被攻击的目标
     * @param monster   怪物
     * @param gameScene 战斗的场景
     */
    public void startAI(Creature target, Monster monster, GameScene gameScene) {
        // 目标死了,不进行攻击
        if (target instanceof Player){
            if (combatService.isPlayerDead((Player) target, monster)) {
                return;
            }
        } else {
            if (target.getHp() <=0 || target.getState() == -1) {
                return;
            }
        }

        // 玩家不在场景内，不进行攻击
        if (null == gameScene.getPlayers().get(target.getId())) {
            return;
        }
        // 怪物死亡了，不进行攻击
        if (monster.getHp() <=0 || monster.getState() == -1) {
            return;
        }


        if ((monster.getAttackTime() + monster.getAttackSpeed()) < System.currentTimeMillis()) {
            // 对玩家进行普通攻击
            monsterAttack(monster, target);
            // 更新普通攻击的攻击时间
            monster.setAttackTime(System.currentTimeMillis());
        }

        // 如果怪物没有技能在冷却中，使用技能
        if (monster.getHasUseSkillMap().size() < 1) {
            monsterUseSkill(monster, target,gameScene);
        }
    }







}




