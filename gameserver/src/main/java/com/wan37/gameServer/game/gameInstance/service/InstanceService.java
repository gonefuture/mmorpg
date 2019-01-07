package com.wan37.gameServer.game.gameInstance.service;


import com.wan37.gameServer.game.combat.service.CombatService;
import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.player.model.Player;
import com.wan37.gameServer.game.scene.model.SceneType;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import com.wan37.gameServer.game.sceneObject.model.NPC;
import com.wan37.gameServer.game.sceneObject.service.GameObjectService;
import com.wan37.gameServer.game.sceneObject.service.MonsterAIService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TimedTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

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
    private TimedTaskManager timedTaskManager;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private GameObjectService gameObjectService;

    @Resource
    private MonsterAIService monsterAIService;

    @Resource
    private CombatService combatService;

    /**
     *  进入副本，将副本与玩家绑定起来
     */
    public GameInstance enterInstance(Player player, Integer instanceId) {

        // 玩家当前的场景
        GameInstance gameInstance = initGameInstance(player,instanceId);

        if (null == gameInstance || null == gameInstance.getInstanceTime())
            return null;

        // 设置玩家的当前副本
        player.setCurrentGameInstance(gameInstance);
        player.setScene(gameInstance.getId());


        log.debug("gameInstance {}",gameInstance);


        return gameInstance;
    }


    /**
     *  下一个副本Boss出场
     * @param gameInstance 副本实例
     */
    private Monster nextBoss(GameInstance gameInstance) {

        List<Monster> monsterList =  gameInstance.getBossList();

        Monster nextBoss = null;
        if (monsterList.size()>0) {
            nextBoss = monsterList.remove(0);
            log.debug("下一个boss {} ,当前boss列表 {}",nextBoss,monsterList);
            // 将Bos放入怪物集合中
            gameInstance.getMonsters().put(nextBoss.getKey(),nextBoss);
            // 设置当前守关Boos
            gameInstance.setGuardBoss(nextBoss);

            // boss出场台词
            notificationManager.notifyScene(gameInstance, MessageFormat.format("\n {0} 说： {1} \n \n",
                    nextBoss.getName(), nextBoss.getTalk()));
        }
        return nextBoss;
    }


    /**
     *  退出副本
     * @param player 玩家
     */
    public void exitInstance(Player player) {
        if (player.getCurrentGameInstance() != null) {
            GameInstance gameInstance = player.getCurrentGameInstance();
            gameInstance.getPlayers().remove(player.getId());

            // 返回原来的场景
            player.setScene(player.getCurrentGameInstance()
                    .getPlayerFrom().get(player.getId()));


            // 设置当前副本实例为空
            player.setCurrentGameInstance(null);
            notificationManager.notifyPlayer(player,"你已经退出副本");
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
    private GameInstance initGameInstance(Player player, Integer instanceId) {
        GameScene gameScene = gameSceneService.findSceneById(instanceId);
        // 如果不是副本，返回null
        if (!gameScene.getType().equals(SceneType.INSTANCE_SCENE.getCode()))
            return null;

        GameInstance gameInstance = new GameInstance();
        BeanUtils.copyProperties(gameScene,gameInstance);

        // 设置副本开始的时间

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

        // 加载第一个boss
        Monster firstBoss =  nextBoss(gameInstance);

        gameInstance.setGuardBoss(firstBoss);

        gameInstance.getPlayers().put(player.getId(), player);
        // 记录玩家原先的位置
        gameInstance.getPlayerFrom().put(player.getId(),player.getScene());

        // 开启场景心跳
        startTick(gameInstance);

        log.debug(" gameInstance.getMonsters() {}", gameInstance.getMonsters());
        return gameInstance;
    }





    /**
     *  开启场景心跳
     * @param gameInstance 场景
     */
    public void startTick(GameInstance gameInstance) {

        // 副本500ms进行心跳一次
        ScheduledFuture<?> attackTask = TimedTaskManager.scheduleWithFixedDelay(0, 500, () -> {

            Monster guardBoss = gameInstance.getGuardBoss();
            Map<Long, Monster> monsterMap = gameInstance.getMonsters();
            if (guardBoss ==null  && gameInstance.getBossList().size() == 0) {
                // 所有Boss死亡，挑战成功
                notificationManager.notifyScene(gameInstance, MessageFormat.format(
                        "恭喜你挑战副本{0}成功 ", gameInstance.getName()));
                gameInstance.getPlayers().values().forEach(this::exitInstance);
            }

            Optional.ofNullable(guardBoss).ifPresent(
                    boss -> {
                        // 如果守关boss死亡，下一个Boss出场，将守关boos移除怪物列表
                        if (boss.getHp() <= 0) {
                            gameInstance.setGuardBoss(nextBoss(gameInstance));
                            monsterMap.remove(guardBoss.getKey());
                        } else {
                            // 如果boss尚未死亡，攻击玩家
                            if (!gameInstance.getFail()) {
                                // 守关boos主动攻击场景内玩家
                                gameInstance.getPlayers().values().forEach(
                                        player -> {
                                            monsterAIService.startAI(player, boss, gameInstance);

                                            if (player.getHp() < 0) {
                                                notificationManager.notifyPlayer(player,"很遗憾，你挑战副本失败");
                                                gameInstance.getPlayers().remove(player.getId());
                                                exitInstance(player);
                                                gameInstance.setFail(true);
                                            }
                                        }
                                );

                            }
                        }
                    }
            );
        });

        // 根据副本存在时间销毁副本定时器
        TimedTaskManager.schedule(gameInstance.getInstanceTime(), () -> {
            log.debug("{}的副本定时器销毁",gameInstance.getName());
            gameInstance.setFail(true);
            gameInstance.getPlayers().values().forEach(this::exitInstance);
            attackTask.cancel(false);
        });

        // 副本存活时间到期， 销毁副本，传送玩家出副本。
        TimedTaskManager.schedule(gameInstance.getInstanceTime(),
                () -> gameInstance.getPlayers().values().forEach(this::exitInstance)
        );

        // 副本关闭通知,提前10000毫秒（10秒）通知
        TimedTaskManager.schedule(gameInstance.getInstanceTime()-10000,
                () -> gameInstance.getPlayers().values().forEach(
                        p ->  notificationManager.notifyPlayer(p,"副本将于十秒后关闭，请准备好传送。"))
        );

    }




}
