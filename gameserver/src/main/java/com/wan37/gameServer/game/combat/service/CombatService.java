package com.wan37.gameServer.game.combat.service;

import com.wan37.common.entity.Msg;
import com.wan37.gameServer.game.sceneObject.service.GameObjectService;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
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
    private GameObjectService gameObjectService;

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private NotificationManager notificationManager;


    public Msg commonAttack(int attack , Creature target,GameScene gameScene){
        if (target.getState() ==  -1) {
            notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("目标 {0} 死亡",target));
            return new Msg(401,"目标已经死亡");
        } else {
            target.setHp(target.getHp() - attack);
            notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                    MessageFormat.format("目标 {0},hp：{1}",target.getName(),target.getHp()));
            if (target.getHp() <0) {
                target.setHp(0L);
                target.setState(-1);
            }
            return new Msg(200,"攻击成功");
        }
    }

    /**
     *  普通攻击服务
     */
    public Msg playerCommonAttack(Player player,Long gameObjectId) {

        GameScene gameScene = gameSceneService.findSceneById(player.getScene());
        Creature target = gameScene.getMonsters().get(gameObjectId);
        // 获取玩家的基础攻击这一属性
        int attack = player.getRolePropertyMap().get(4).getValue();

        notificationManager.<String>notifyScenePlayerWithMessage(gameScene,
                MessageFormat.format("玩家{0}  向{1} 普通攻击,攻击力为 {2}",player.getName(),target.getName(), attack));
        log.debug("玩家的普通攻击力 {}",attack);
        return commonAttack(attack, target, gameScene);
    }



}
