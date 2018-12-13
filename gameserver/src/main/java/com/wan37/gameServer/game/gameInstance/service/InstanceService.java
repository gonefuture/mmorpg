package com.wan37.gameServer.game.gameInstance.service;


import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;
import com.wan37.gameServer.game.gameSceneObject.model.NPC;
import com.wan37.gameServer.game.gameSceneObject.service.GameObjectService;
import com.wan37.gameServer.game.gameSceneObject.service.MonsterAIService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/11/26 16:51
 * @version 1.00
 * Description: 副本服务。
 */

@Slf4j
@Service
public class InstanceService {

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private TaskManager taskManager;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private GameObjectService gameObjectService;


    @Resource
    private MonsterAIService monsterAIService;

    /**
     *  进入副本，将副本与玩家绑定起来
     */
    public GameInstance enterInstance(Player player, Integer instanceId) {
        // 玩家当前的场景
        GameInstance gameInstance = init(player,instanceId);

        if (null == gameInstance || null == gameInstance.getInstanceTime())
            return null;

        // 设置玩家的当前副本
        player.setCurrentGameInstance(gameInstance);
        player.setScene(gameInstance.getId());

        // 如果有守关Boss则进行攻击
        Optional.ofNullable(gameInstance.getGuardBoss()).
                ifPresent(guard -> monsterAIService.startBossAttackAi(guard,gameInstance));



        // 副本关闭通知
        taskManager.schedule(gameInstance.getInstanceTime()-10000,() -> {
            notificationManager.notifyPlayer(player,"副本将于十秒后关闭，请准备好传送。");
            return null;
        });

        // 定时销毁副本，传送玩家出副本。
        taskManager.schedule(gameInstance.getInstanceTime(), () -> {
            exitInstance(player);
            return null;
        });

        return gameInstance;
    }


    /**
     *  下一个副本Boss出场
     * @param gameInstance 副本实例
     */
    public Monster nextBoss(Long preBossId,GameInstance gameInstance) {
        gameInstance.getMonsters().remove(preBossId);
        List<Monster> monsterList =  gameInstance.getBossList();

        Monster nextBoss = null;
        if (monsterList.size()>0) {
            nextBoss = monsterList.remove(0);
            // 将Bos放入怪物集合中
            gameInstance.getMonsters().put(nextBoss.getKey(),nextBoss);
            // 设置当前守关Boos
            gameInstance.setGuardBoss(nextBoss);
        }
        return nextBoss;
    }


    /**
     *  退出副本
     * @param player 玩家
     */
    public void exitInstance(Player player) {
        if (player.getCurrentGameInstance() != null) {
            // 返回原来的场景
            player.setScene(player.getCurrentGameInstance()
                    .getPlayerFrom().get(player.getId()));


            // 设置当前副本实例为空
            player.setCurrentGameInstance(null);
            notificationManager.notifyPlayer(player,"挑战失败，副本已关闭，你已被传送");
        } else {
            notificationManager.notifyPlayer(player,"你不在副本中");
        }
    }



    /**
     *  初始化的副本实例
     * @param player 玩家
     * @param instanceId 副本id
     * @return 一个初始化好的副本实例
     */
    private GameInstance init(Player player, Integer instanceId) {
        GameScene gameScene = gameSceneService.findSceneById(instanceId);
        GameInstance gameInstance = new GameInstance();
        BeanUtils.copyProperties(gameScene,gameInstance);

        // 加载怪物和boss

        String  gameObjectIds = gameInstance.getGameObjectIds();
        Arrays.stream(gameObjectIds.split(","))
                .map(Long::valueOf)
                .map( gameObjectService::getGameObject)
                .forEach( sceneObject -> {
                            if ( sceneObject.getRoleType() == 1) {
                                NPC npc = new NPC();
                                BeanUtils.copyProperties(sceneObject,npc);
                                gameInstance.getNpcs().put(sceneObject.getId(), npc);
                            }
                            // 加载boss进怪物列表
                            if (sceneObject.getRoleType() == 3) {
                                Monster monster = new Monster();
                                BeanUtils.copyProperties(sceneObject,monster);
                                gameInstance.getBossList().add(monster);
                            }
                        }
                );

        Monster firstBoss =  gameInstance.getBossList().remove(0);
        gameInstance.getMonsters().put(firstBoss.getKey(),firstBoss);



        gameInstance.getPlayers().put(player.getId(), player);
        // 记录玩家原先的位置
        gameInstance.getPlayerFrom().put(player.getId(),player.getScene());

        log.debug(" gameInstance.getMonsters() {}", gameInstance.getMonsters());
        return gameInstance;
    }



}
