package com.wan37.gameserver.game.gameInstance.service;


import com.wan37.gameserver.game.gameInstance.model.GameInstance;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.scene.manager.SceneCacheMgr;
import com.wan37.gameserver.game.scene.model.SceneType;
import com.wan37.gameserver.game.sceneObject.model.Monster;
import com.wan37.gameserver.game.sceneObject.model.NPC;
import com.wan37.gameserver.game.sceneObject.service.GameObjectService;
import com.wan37.gameserver.game.sceneObject.service.MonsterAiService;
import com.wan37.gameserver.game.scene.model.GameScene;
import com.wan37.gameserver.game.scene.servcie.GameSceneService;
import com.wan37.gameserver.manager.notification.NotificationManager;
import com.wan37.gameserver.manager.task.TimedTaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

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
    private NotificationManager notificationManager;

    @Resource
    private GameObjectService gameObjectService;

    @Resource
    private MonsterAiService monsterAIService;




    public boolean isInInstance(Player player) {
        return player.getCurrentScene().getType().equals(SceneType.INSTANCE_SCENE.getType());
    }



    /**
     *  进入副本，将副本与玩家绑定起来
     */
    public GameInstance enterInstance(Player player, Integer instanceId) {

        // 初始化好的副本场景
        GameInstance gameInstance = initGameInstance(instanceId);

        if (Objects.isNull(gameInstance) || Objects.isNull(gameInstance.getInstanceTime())) {
            return null;
        }
        // 记录玩家原先的位置
        gameInstance.getPlayerFrom().put(player.getId(),player.getScene());
        // 进入副本
        gameSceneService.moveToScene(player,gameInstance);

        return gameInstance;
    }





    public GameInstance enterTeamInstance(Collection<Player> players, Integer instanceId) {
        // 初始化好的副本场景
        GameInstance gameInstance = initGameInstance(instanceId);

        if (Objects.isNull(gameInstance) || Objects.isNull(gameInstance.getInstanceTime())) {
            return null;
        }
        players.forEach(
                player -> {
                    // 记录玩家原先的位置
                    gameInstance.getPlayerFrom().put(player.getId(),player.getScene());
                    // 进入副本
                    gameSceneService.moveToScene(player,gameInstance);
                }
        );

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
            log.debug("下一个boss {} ,当前boss列表 {}",nextBoss.getName(),monsterList);
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
    public void exitInstance(Player player,GameInstance gameInstance) {
        gameInstance.getPlayers().remove(player.getId());

        // 返回原来的场景
        player.setScene(gameInstance.getPlayerFrom().get(player.getId()));
        gameSceneService.initPlayerScene(player);
        log.debug("返回原来的厂场景",player.getCurrentScene());

        // 设置当前副本实例为空
        notificationManager.notifyPlayer(player,"你已经退出副本");
    }



    /**
     *  初始化的副本实例
     * @param instanceId 副本id
     * @return 一个初始化好的副本实例
     */
    private GameInstance initGameInstance( Integer instanceId) {
        GameScene sceneTemplate = SceneCacheMgr.getScene(instanceId);
        // 如果不是副本，返回null
        if (!sceneTemplate.getType().equals(SceneType.INSTANCE_SCENE.getType())) {
            return null;
        }
        GameInstance gameInstance = new GameInstance();

        gameInstance.setId(sceneTemplate.getId());
        gameInstance.setName(sceneTemplate.getName());
        gameInstance.setType(SceneType.INSTANCE_SCENE.getType());
        // 设置副本开始的时间
        gameInstance.setInstanceTime(sceneTemplate.getInstanceTime());

        // 加载怪物和boss
        String  gameObjectIds = sceneTemplate.getGameObjectIds();
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
        // 开启场景心跳
        startTick(gameInstance);
        return gameInstance;
    }





    /**
     *  开启场景心跳
     * @param gameInstance 场景
     */
    private void startTick(GameInstance gameInstance) {

        // 副本60ms进行心跳一次

        ScheduledFuture<?> attackTask = gameInstance.getSingleThreadSchedule().scheduleWithFixedDelay( () -> {

            Monster guardBoss = gameInstance.getGuardBoss();
            Map<Long, Monster> monsterMap = gameInstance.getMonsters();
            if (guardBoss ==null  && gameInstance.getBossList().size() == 0) {
                // 所有Boss死亡，挑战成功
                gameInstance.getPlayers().values().forEach(
                        p ->    notificationManager.notifyScene(gameInstance, MessageFormat.format(
                                "恭喜你挑战副本{0}成功 ", gameInstance.getName()))
                );
                // 退出副本
                gameInstance.getPlayers().values().forEach(p -> exitInstance(p,gameInstance));
            }
            Optional.ofNullable(guardBoss).ifPresent(
                    boss -> {
                        // 如果守关boss死亡，下一个Boss出场，将守关boos移除怪物列表
                        if (boss.getHp() <= 0) {
                            gameInstance.setGuardBoss(nextBoss(gameInstance));
                            monsterMap.remove(guardBoss.getKey());
                        } else {
                            // 如果boss尚未死亡，检测玩家玩家状态
                            if (!gameInstance.getFail()) {
                                // 设置怪物的攻击目标
                                if (Objects.isNull(boss.getTarget())) {
                                    gameInstance.getPlayers().values().stream().findAny()
                                            .ifPresent(boss::setTarget);
                                }
                                gameInstance.getPlayers().values().forEach(
                                        player -> {
                                            if (player.getHp() < 0) {
                                                notificationManager.notifyPlayer(player,"很遗憾，你挑战副本失败");
                                                exitInstance(player,gameInstance);
                                            }
                                        }
                                );
                            }
                        }
                    }
            );
            gameInstance.getMonsters().values().forEach( m -> {
                if (Objects.nonNull(m.getTarget())) {
                    monsterAIService.startAI(m, gameInstance);
                }});

        },20,60, TimeUnit.MILLISECONDS);

        // 副本存活时间到期， 销毁副本，传送玩家出副本。 根据副本存在时间销毁副本定时器
        TimedTaskManager.schedule(gameInstance.getInstanceTime(), () -> {
            gameInstance.getPlayers().values().forEach(p -> exitInstance(p,gameInstance));
            attackTask.cancel(false);
            destroyInstance(gameInstance);
        });

        // 副本关闭通知,提前10000毫秒（10秒）通知
        TimedTaskManager.schedule(gameInstance.getInstanceTime()-10000,
                () -> gameInstance.getPlayers().values().forEach(
                        p ->  notificationManager.notifyPlayer(p,"副本将于十秒后关闭，请准备好传送。"))
        );

    }


    /**
     *  重要，及时销毁副本
     * @param gameInstance 副本
     */
    private void destroyInstance(GameInstance gameInstance){
        gameInstance.setFail(true);
        GameInstance finalGameInstance = gameInstance;
        gameInstance.getPlayers().values().forEach(p -> exitInstance(p, finalGameInstance));
        log.debug("{}的副本定时器销毁",gameInstance.getName());
        gameInstance = null;

    }





}
