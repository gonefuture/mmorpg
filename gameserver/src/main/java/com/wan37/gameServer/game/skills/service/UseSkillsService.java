package com.wan37.gameServer.game.skills.service;

import com.wan37.gameServer.game.gameSceneObject.model.Monster;

import com.wan37.gameServer.game.gameSceneObject.service.MonsterDropsService;
import com.wan37.gameServer.game.scene.model.GameScene;

import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.game.skills.manager.SkillsCacheMgr;
import com.wan37.gameServer.game.skills.model.Skill;
import com.wan37.gameServer.manager.notification.NotificationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 12:22
 * @version 1.00
 * Description: 使用技能
 */

@Service
@Slf4j
public class UseSkillsService {

    @Resource
    private PlayerDataService playerDataService;

    @Resource
    private SkillsService skillsService;

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private SkillsCacheMgr skillsCacheMgr;

    @Resource
    private MonsterDropsService monsterDropsService;

    @Resource
    private NotificationManager notificationManager;



    public Monster attackMonsterBySkill(String channelId, int skillId, long gameObjectId) {
        Player player = playerDataService.getPlayer(channelId);
        if (player == null)
            return null;
        // 获取角色所在场景中的怪物
        GameScene gameScene = gameSceneService.findSceneById(player.getScene());
        Monster monster = gameScene.getMonsters().get(gameObjectId);

        Skill skill =  skillsCacheMgr.get(skillId);
        if ( monster != null && skill != null &&
        canUseSkill(player,skill)
                ) {
            // 消耗角色的mp
            player.setMp(player.getMp() - skill.getMpConsumption());

            // 损伤怪物或NPC的hp
            monster.setHp(monster.getHp() - skill.getHpLose());
            if (monster.getHp() <0 ) {
                // 如果血量小于零，标记游戏对象为死亡状态。
                monster.setState(-1);
                monster.setHp((long)0);
                monster.setDeadTime(System.currentTimeMillis());

                // 结算掉落，这里暂时直接放到背包里
                monsterDropsService.dropItem(player,monster);

                // 广播事件

                notificationManager.<Monster>notifyScenePlayerWithMessage(gameScene,monster);


            }

            gameScene.getMonsters().put(monster.getId(),monster);
            return monster;
        } else {
            return null;
        }
    }



    /**
    *   检验觉角色是否能使用技能。
     *   （注意，没有做空指针检查）
     */
    public boolean canUseSkill(Player player, Skill skill) {
        // 角色状态为-1时角色死亡不能使用技能，mp小于技能mp消耗时不能使用技能
        if (player.getState() != -1
                && player.getMp()>skill.getMpConsumption()
                // 检查技能是否处于冷却状态
                && skillsService.checkCD(player,skill)
                ) {
            return true;
        } else {
            return false;
        }
    }




}
