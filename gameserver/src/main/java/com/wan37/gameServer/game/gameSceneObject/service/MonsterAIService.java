package com.wan37.gameServer.game.gameSceneObject.service;

import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;
import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

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

    @Resource
    private TaskManager taskManager;


    /**
     *  怪物进行攻击
     * @param monster 怪物
     * @param target 目标
     */
    public void monsterAttack(Monster monster, Player target) {
        Integer attack = monster.getAttack();
        target.setHp(target.getHp() - attack);
        notificationManager.notifyPlayer(target, MessageFormat.format("{0}在攻击你， 对你造成了{1}点伤害，你当前的hp为 {3} ",
                monster.getName(), monster.getAttack(),target.getHp()));
        combatService.isPlayerDead(target,monster);
    }


    /**
     *      怪物使用技能，从怪物拥有的技能随机触发
     * @param monster 怪物
     * @param target 怪物攻击的目标
     */
    public void monsterUseSkill(Monster monster, Player target) {

        Arrays.stream(monster.getSkills().split(","))
                .map(Integer::valueOf)
                .findAny().ifPresent(
                skillId -> { // 如果技能存在，则释放技能
                    Skill skill = skillsService.getSkill(skillId);

                    if (skillsService.useSkill(monster,target,skill)) {
                        notificationManager.notifyPlayer(target,
                                MessageFormat.format("{0} 对你使用技能 {1}， 对你造成了{1}点伤害，你当前的hp为 {3} ",
                                        monster.getName(), monster.getAttack(),target.getHp()));
                    }
                }
        );
    }

    /**
     *  开启怪物主动攻击
     * @param monster 怪物
     * @param gameScene 场景
     */
    public void startBossAttackAi(Monster monster, GameScene gameScene) {
        // 攻击进入入副本的所有玩家
        gameScene.getPlayers().values().forEach(
                player -> {
                    // 固定普通攻击频率，每两秒攻击一次。可改进成在策划表配置或根据武器配置
                    taskManager.scheduleAtFixedRate(2, 2, () -> {
                        monsterAttack(monster, player);
                        combatService.isPlayerDead(player, monster);
                    });


                    // 如果怪物没有技能在冷却中，使用技能
                    if (monster.getSkillMap().size() < 1) {
                        monsterUseSkill(monster, player);
                    }
                }
        );
    }


}




