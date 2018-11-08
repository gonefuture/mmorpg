package com.wan37.gameServer.game.combat.service;

import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.SceneObject.service.GameObjectService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    private GameObjectService gameObjectService;


    public Msg commonAttack(int attack , Creature target){
        if (target.getState() ==  -1) {
            return new Msg(401,"目标已经死亡");
        } else {
            target.setHp(target.getHp() - attack);
            return new Msg(200,"攻击成功");
        }
    }

    public Msg playerCommonAttack(Player player,Integer gameObjectId) {
        Creature target = gameObjectService.getGameObject(gameObjectId);
        // 获取玩家的基础攻击这一属性
        int attack = player.getRolePropertyMap().get(4).getValue();
        return commonAttack(attack,target);
    }



}
