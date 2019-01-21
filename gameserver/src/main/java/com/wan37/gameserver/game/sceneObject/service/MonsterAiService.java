package com.wan37.gameserver.game.sceneObject.service;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.AttackMonsterEvent;
import com.wan37.gameserver.event.model.MonsterEventDeadEvent;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.sceneObject.model.Monster;
import com.wan37.gameserver.game.skills.model.Pet;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.game.skills.service.SkillsService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/12/12 14:22
 * @version 1.00
 * Description: 怪物AI服务
 */

@Slf4j
@Service
public class MonsterAiService {


    @Resource
    private NotificationManager notificationManager;



    @Resource
    private SkillsService skillsService;


    @Resource
    private GameObjectService gameObjectService;

    @Resource
    private MonsterDropsService monsterDropsService;

    @Resource
    private PlayerDataService playerDataService;


    /**
     *  怪物进行攻击
     * @param monster 怪物
     * @param target 目标
     */
    private void monsterAttack(Monster monster, Creature target, GameScene gameScene) {
        Long attack = monster.getAttack();
        target.setHp(target.getHp() - attack);

        notificationManager.notifyScene(gameScene,
                MessageFormat.format("{0}在攻击{1}，造成了{2}点伤害，{3}当前的hp为 {4} \n",
                        monster.getName(),target.getName(), monster.getAttack(),target.getName(),target.getHp()));

        if (target instanceof Player) {
            playerDataService.isPlayerDead((Player) target,monster);
        } else {
            monsterBeAttack(monster,(Monster)target,gameScene,attack);
        }

    }


    /**
     *     怪物使用技能，从怪物拥有的技能随机触发
     * @param monster 怪物
     * @param target 怪物攻击的目标
     */
    private void monsterUseSkill(Monster monster, Creature target, GameScene gameScene) {

        Arrays.stream(monster.getSkills().split(","))
                .map(Integer::valueOf).parallel()
                // 随机返回一个技能id
                .findAny().ifPresent(
                skillId -> {
                    // 如果技能存在，则释放技能
                    Skill skill = skillsService.getSkill(skillId);
                    if (skillsService.castSkill(monster,target,gameScene,skill)) {
                        if (target instanceof Player) {
                            playerDataService.isPlayerDead((Player) target,monster);
                        }  else {
                            monsterBeAttack(monster,(Monster)target,gameScene,skill.getHurt());
                        }
                    }
                }
        );
    }


    /**
     *  怪物（包括玩家宠物）自动攻击
     * @param monster   怪物
     * @param gameScene 战斗的场景
     */
    public void startAI(Monster monster, GameScene gameScene) {
        Creature target = monster.getTarget();
        // 目标死了,不进行攻击
        if (target instanceof Player){
            // 玩家不在场景内，不进行攻击
            if (null == gameScene.getPlayers().get(target.getId())) {
                monster.setTarget(null);
                return;
            }

            if (playerDataService.isPlayerDead((Player) target, monster)) {
                monster.setTarget(null);
                return;
            }
        }

        if (target.getHp() <=0 || target.getState() == -1) {
            monster.setTarget(null);
            return;
        }

        // 怪物死亡了，不进行攻击
        if (monster.getHp() <=0 || monster.getState() == -1) {
            monster.setTarget(null);
            return;
        }

        if ((monster.getAttackTime() + monster.getAttackSpeed()) < System.currentTimeMillis()) {
            // 进行普通攻击
            monsterAttack(monster, target,gameScene);

            // 更新普通攻击的攻击时间
            monster.setAttackTime(System.currentTimeMillis());
        }
        // 使用没有冷却的技能
        if (monster.getHasUseSkillMap().size() < 1) {
            monsterUseSkill(monster, target,gameScene);
        }
    }



    /**
     *  怪物被攻击并广播
     * @param creature 玩家
     * @param monster    怪物目标
     * @param gameScene 游戏场景
     * @param damage    伤害
     */
    public void notifyMonsterBeAttack(Creature creature,Monster monster,GameScene gameScene,Long damage) {

        notificationManager.notifyScene(gameScene,
                MessageFormat.format("{0} 受到 {1} 攻击，hp减少{2},当前hp为 {3} \n"
                        ,monster.getName(),creature.getName(),damage ,monster.getHp()));
        monsterBeAttack(creature,monster,gameScene,damage);
    }




    /**
     *  怪物被攻击
     * @param creature 玩家
     * @param monster    怪物目标
     * @param gameScene 游戏场景
     * @param damage    伤害
     */
    public void monsterBeAttack(Creature creature,Monster monster,GameScene gameScene,Long damage) {

        // 将怪物当前目标设置为玩家,让怪物攻击玩家
        if (Objects.isNull(monster.getTarget())) {
            monster.setTarget(creature);
        }


        Player player = null;
        if (creature instanceof Player) {
            player = (Player) creature;
        }

        if (creature instanceof Pet && ((Pet) creature).getMaster() instanceof Player) {
            Pet pet = (Pet) creature;
            player =  (Player) pet.getMaster();
        }

        if (Objects.nonNull(player)) {
            // 怪物被攻击的事件
            EventBus.publish(new AttackMonsterEvent(player,monster,gameScene,damage));
            // 如果怪物死亡
            if (gameObjectService.sceneObjectAfterDead(monster)) {
                // 结算掉落，这里暂时直接放到背包里
                monsterDropsService.dropItem(player,monster);
                // 怪物死亡的事件
                EventBus.publish(new MonsterEventDeadEvent(player,monster,gameScene,damage));
            }
        }

    }





}




