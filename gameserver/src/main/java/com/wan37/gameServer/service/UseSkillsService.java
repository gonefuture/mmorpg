package com.wan37.gameServer.service;

import com.wan37.gameServer.entity.GameScene;
import com.wan37.gameServer.entity.Monster;
import com.wan37.gameServer.entity.NPC;
import com.wan37.gameServer.entity.Player;
import com.wan37.gameServer.manager.cache.SkillsCacheMgr;
import com.wan37.mysql.pojo.entity.TGameObject;
import com.wan37.mysql.pojo.entity.TSkill;
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



    public Monster attackMonsterBySkill(String channelId, int skillId, long gameObjectId) {
        Player player = playerDataService.getPlayer(channelId);
        if (player == null)
            return null;
        // 获取角色所在场景中的怪物
        GameScene gameScene = gameSceneService.findTScene(player.getScene());
        Monster monster = gameScene.getMonsters().get(gameObjectId);

        TSkill tSkill =  skillsCacheMgr.get(skillId);

        if ( monster != null && tSkill != null &&
        canUseSkill(player,tSkill)
                ) {
            // 消耗角色的mp
            player.setMp(player.getMp() - tSkill.getMpConsumption());

            // 损伤怪物或NPC的hp
            monster.setHp(monster.getHp() - tSkill.getHpLose());
            if (monster.getHp() <0 ) {
                // 如果血量小于零，标记游戏对象为死亡状态。
                monster.setState(-1);
                monster.setHp((long)0);
                monster.setDeadTime(System.currentTimeMillis());
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
    public boolean canUseSkill(Player player, TSkill tSkill) {
        // 角色状态为-1时角色死亡不能使用技能，mp小于技能mp消耗时不能使用技能
        if (player.getState() != -1
                && player.getMp()>tSkill.getMpConsumption()
                // 检查技能是否处于冷却状态
                && skillsService.checkCD(player,tSkill)
                ) {
            return true;
        } else {
            return false;
        }
    }




}
