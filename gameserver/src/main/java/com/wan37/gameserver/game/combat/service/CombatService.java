package com.wan37.gameserver.game.combat.service;

import com.wan37.gameserver.event.EventBus;
import com.wan37.gameserver.event.model.PKEvent;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.sceneObject.model.Monster;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.sceneObject.service.MonsterAiService;
import com.wan37.gameserver.game.sceneObject.service.MonsterDropsService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.game.skills.model.Skill;
import com.wan37.gameserver.game.skills.model.SkillType;
import com.wan37.gameserver.game.skills.service.SkillsService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/8 15:19
 * @version 1.00
 * Description: mmorpg
 */


@Service
@Slf4j
public class CombatService {


    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private MonsterDropsService monsterDropsService;

    @Resource
    private SkillsService skillsService;


    @Resource
    private MonsterAiService monsterAIService;




    /**
     *  普通攻击服务
     */
    public void commonAttack(Player player, Long gameObjectId) {

        GameScene gameScene = gameSceneService.getSceneByPlayer(player);
        Monster target;

        target = gameScene.getMonsters().get(gameObjectId);

        if (target == null) {
            notificationManager.notifyPlayer(player,"攻击的目标不存在");
            return;
        }

        // 将场景对象的当前目标设置为玩家
        target.setTarget(player);

        // 攻击力
        int attack = player.getAttack();

        notificationManager.notifyScene(gameScene,
                MessageFormat.format("玩家{0}  向 {1} 发动普通攻击,攻击力为 {2} \n",player.getName(),target.getName(), attack));

        if (target.getState() ==  -1) {
            notificationManager.notifyScene(gameScene,
                    MessageFormat.format("目标 {0} 已经死亡 \n",target.getName()));
            notificationManager.notifyPlayer(player,"不能攻击，目标已经死亡 \n");
        } else {
            target.setHp(target.getHp() - attack);
            monsterAIService.monsterBeAttack(player,target,gameScene,player.getAttack());
        }
    }


    /**
     *  pvp普通攻击
     * @param player 玩家
     * @param targetPlayer 目标玩家
     */
    public void commonAttackByPVP(Player player, Player targetPlayer) {
        GameScene gameScene = gameSceneService.getSceneByPlayer(player);

        // 获取发起攻击者的战力
        int attack = player.getAttack();
        notificationManager.notifyScene(gameScene,
                MessageFormat.format("玩家 {0} 向 {1} 发起攻击，攻击力是{2} \n",
                        player.getName(),targetPlayer.getName(),attack));

        // 结算攻击
        targetPlayer.setHp(targetPlayer.getHp() - attack);

        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, attack);

        // 检测玩家是否死亡
        if (playerDataService.isPlayerDead(targetPlayer,player)) {
            // 如果目标死亡，玩家pk胜利,抛出pk胜利事件
            EventBus.publish(new PKEvent(player,true));

        }
    }




    /**
     *    使用技能进行pvp,自动识别单体技能和群体技能
     * @param player 玩家
     * @param skillId 技能di
     * @param targetIdList 目标列表
     */
    public void useSkillPVP(Player player, Integer skillId, List<Long> targetIdList) {
        Skill skill = skillsService.getSkill(skillId);
        if (!skillsService.canSkill(player,skill)) {
            return;
        }
        if (targetIdList.size() > 1 && skill.getSkillType().equals(SkillType.ATTACK_MULTI.getTypeId())) {
            notificationManager.notifyPlayer(player,"该技能不能对多个目标使用");
            return;
        }

        if (targetIdList.size() >1) {
            targetIdList.forEach(
                    targetId -> {
                        skillPVP(player,skill,targetId);
                    }
            );
        } else {
            skillPVP(player,skill, targetIdList.get(0));
        }
    }


    /**
     *  在PVP中使用技能攻击
     * @param player 玩家
     * @param skill 技能
     * @param targetId 目标玩家
     */
    private void skillPVP(Player player, Skill skill, Long targetId) {
        GameScene gameScene = gameSceneService.getSceneByPlayer(player);
        Player targetPlayer = gameScene.getPlayers().get(targetId);
        if (null == targetPlayer) {
            notificationManager.notifyPlayer(player,"目标不存在此场景，可能已离开或下线");
            return;
        }

        if (player.getId().equals(targetId)) {
            notificationManager.notifyPlayer(player,"自己不能攻击自己");
            return;
        }

        if (!skillsService.castSkill(player,targetPlayer,gameScene, skill)) {
            notificationManager.notifyPlayer(player,"使用技能失败，可能是mp不足");
            return;
        }

        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),targetPlayer.getName(),skill.getName()));


        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, skill.getHurt());

        // 检测玩家是否死亡
        if (playerDataService.isPlayerDead(targetPlayer,player)) {
            // 如果目标死亡，玩家pk胜利,抛出pk胜利事件
                EventBus.publish(new PKEvent(player,true));

        }
    }


    /**
     *  使用技能攻击怪物
     * @param ctx 上下文
     * @param skillId 技能id
     * @param targetIdList 目标id列表
     */
    public void useSkillAttackMonster(ChannelHandlerContext ctx,Integer skillId,  List<Long> targetIdList ) {
        Player player = playerDataService.getPlayerByCtx(ctx);
        Skill skill = skillsService.getSkill(skillId);
        if (!skillsService.canSkill(player,skill)) {
            return;
        }
        GameScene gameScene = gameSceneService.getSceneByPlayer(player);


        if (targetIdList.size() > 1 && !skill.getSkillType().equals(SkillType.ATTACK_MULTI.getTypeId())) {
            notificationManager.notifyPlayer(player,"该技能不能对多个目标使用");
            return;
        }

        if (targetIdList.size() >1) {
            targetIdList.forEach(
                    targetId -> {
                        skillPVE(player,targetId,gameScene,skill);
                    }
            );
        } else {
            skillPVE(player,targetIdList.get(0),gameScene,skill);
        }
    }


    /**
     *  PVE
     * @param player 玩家
     * @param targetId id
     * @param gameScene 场景
     * @param skill 技能
     */
    public void skillPVE(Player player,Long targetId, GameScene gameScene, Skill skill){
        Monster target = gameScene.getMonsters().get(targetId);


        if (null == target) {
            notificationManager.notifyPlayer(player,"目标怪物不存在此场景");
            return;
        }
        // 将怪物当前目标设置为玩家,让怪物攻击玩家
        target.setTarget(player);

        // 使用技能
        if (!skillsService.castSkill(player,target,gameScene, skill)) {
            notificationManager.notifyPlayer(player,"使用技能失败，可能是mp不足的原因");
            return;
        }
        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),target.getName(),skill.getName()));
        //
        monsterAIService.monsterBeAttack(player,target,gameScene,skill.getHurt().intValue());

    }



}
