package com.wan37.gameServer.game.combat.service;

import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.gameRole.manager.RoleClassManager;
import com.wan37.gameServer.game.gameRole.model.RoleClass;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.sceneObject.service.GameObjectService;
import com.wan37.gameServer.game.sceneObject.service.MonsterAIService;
import com.wan37.gameServer.game.sceneObject.service.MonsterDropsService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TimedTaskManager;
import com.wan37.gameServer.model.Creature;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.awt.event.MouseAdapter;
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
    private MonsterAIService monsterAIService;




    /**
     *  普通攻击服务
     */
    public void commonAttack(Player player, Long gameObjectId) {

        GameScene gameScene = gameSceneService.findSceneById(player.getScene());
        Monster target;

        // 地图类型为2，则从玩家所在的副本里取出怪物
        if (gameScene.getType() == 2) {
            gameScene = player.getCurrentGameInstance();
            target = gameScene.getMonsters().get(gameObjectId);
        } else {
            target = gameScene.getMonsters().get(gameObjectId);
        }

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
     * @param targetId 目标id
     */
    public void commonAttackByPVP(Player player, Long targetId) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        Player targetPlayer = gameScene.getPlayers().get(targetId);

        if (player.getId().equals(targetId)) {
            notificationManager.notifyPlayer(player,"自己不能攻击自己");
            return;
        }
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
        playerDataService.isPlayerDead(targetPlayer,player);
    }




    /**
     *    使用技能进行pvp,自动识别单体技能和群体技能
     * @param player 玩家
     * @param skillId 技能di
     * @param targetIdList 目标列表
     */
    public void useSkillPVP(Player player, Integer skillId, List<Long> targetIdList) {
        Skill skill = skillsService.getSkill(skillId);
        if (!skillsService.canSkill(player,skill))
            return;

        if (targetIdList.size() > 1 && skill.getSkillsType() !=3) {
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
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        Player targetPlayer = gameScene.getPlayers().get(targetId);
        if (null == targetPlayer) {
            notificationManager.notifyPlayer(player,"目标不存在此场景，可能已离开或下线");
            return;
        }

        if (player.getId().equals(targetId)) {
            notificationManager.notifyPlayer(player,"自己不能攻击自己");
            return;
        }

        if (!skillsService.useSkill(player,targetPlayer,gameScene, skill)) {
            notificationManager.notifyPlayer(player,"使用技能失败，可能是mp不足");
            return;
        }

        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),targetPlayer.getName(),skill.getName()));


        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, skill.getHpLose());

        // 检测玩家是否死亡
        playerDataService.isPlayerDead(targetPlayer,player);
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
        if (!skillsService.canSkill(player,skill))
            return;
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);


        if (targetIdList.size() > 1 && skill.getSkillsType() !=3) {
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
        if (!skillsService.useSkill(player,target,gameScene, skill)) {
            notificationManager.notifyPlayer(player,"使用技能失败，可能是mp不足的原因");
            return;
        }
        notificationManager.notifyScene(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),target.getName(),skill.getName()));
        //
        monsterAIService.monsterBeAttack(player,target,gameScene,player.getAttack());

    }



}
