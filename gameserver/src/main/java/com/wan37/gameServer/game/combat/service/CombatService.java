package com.wan37.gameServer.game.combat.service;

import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameSceneObject.service.MonsterDropsService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.game.skills.service.SkillsService;
import com.wan37.gameServer.game.skills.service.UseSkillsService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;

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
    private UseSkillsService useSkillsService;

    @Resource
    private SkillsService skillsService;






    /**
     *  普通攻击服务
     */
    public Msg playerCommonAttack(Player player, Long gameObjectId) {

        GameScene gameScene = gameSceneService.findSceneById(player.getScene());
        Monster target = gameScene.getMonsters().get(gameObjectId);

        if (target == null) {
            return new Msg(404,"攻击的目标不存在");
        }
        // 攻击力
        int attack = player.getAttack();
        notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家{0}  向 {1} 发动普通攻击,攻击力为 {2} \n",player.getName(),target.getName(), attack));
        log.debug("玩家的普通攻击力 {}",attack);
        if (target.getState() ==  -1) {
            notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("目标 {0} 已经死亡 \n",target.getName()));
            return new Msg(401,"不能攻击，目标已经死亡 \n");
        } else {
            target.setHp(target.getHp() - attack);
            // 重要，设置死亡时间
            target.setDeadTime(System.currentTimeMillis());

            if (target.getHp() <= 0) {
                target.setHp(0L);
                target.setState(-1);
                // 结算掉落，这里暂时直接放到背包里
                monsterDropsService.dropItem(player,target);
            }
            notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("{0} 受到{1}的攻击，hp减少{2},当前hp为 {3} \n"
                            ,target.getName(),player.getName(),attack, target.getHp()));
            return new Msg(200,"\n"+player.getName()+"使用普通攻击成功 \n");
        }
    }



    public Msg commonAttackByPVP(Player player, Long targetId) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);
        Player targetPlayer = playerDataService.getOnlinePlayerById(targetId);
        // 获取发起攻击者的战力
        int attack = player.getAttack();
        notificationManager.notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家 {0} 向 {1} 发起攻击，攻击力是{2} \n",
                        player.getName(),targetPlayer.getName(),attack));

        // 结算攻击
        targetPlayer.setHp(targetPlayer.getHp() - attack);

        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, attack);

        // 检测玩家是否死亡
        isPlayerDead(targetPlayer,player);
        return new Msg(200,"\n普通攻击成功\n");
    }


    public void  isPlayerDead(Player casualty, Player murderer) {

        if (casualty.getHp() < 0){
            casualty.setHp((long)0);
            casualty.setState(-1);

            // 广播并通知死亡的玩家
            notificationManager.playerDead(murderer,casualty);

            gameSceneService.carryToScene(casualty,12);
            notificationManager.notifyPlayer(casualty,casualty.getName()+"  你已经在墓地了 \n");

        }

    }


    /**
     *  在PVP中使用技能攻击
     * @param player 玩家
     * @param skillId 技能id
     * @param targetId 目标玩家id
     * @return 结果
     */
    public Msg skillPVP(Player player, Integer skillId, Long targetId) {
        GameScene gameScene = gameSceneService.findSceneByPlayer(player);

        Skill skill = skillsService.getSkill(skillId);
        if ( null == skill)
            return  new Msg(404,"您不能使用该技能");

        Player targetPlayer = playerDataService.getOnlinePlayerById(targetId);
        if (null == targetPlayer)
            return new Msg(404,"目标不存在此场景，可能已离开或下线");

        notificationManager.notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format(" {0}  对 {1} 使用了 {2} 技能",
                        player.getName(),targetPlayer.getName(),skill.getName()));


        if (!skillsService.useSkill(player,targetPlayer,skill)) {
            return new Msg(404,"技能调用失败，可能是mp不足");
        }

        // 通知攻击结果
        notificationManager.playerBeAttacked(player,targetPlayer, skill.getHpLose());

        // 检测玩家是否死亡
        isPlayerDead(targetPlayer,player);


        return new Msg(404,"技能调用使用成功");

    }




}
