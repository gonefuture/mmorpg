package com.wan37.gameServer.game.gameInstance.service;


import com.wan37.gameServer.game.gameInstance.model.GameInstance;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.gameSceneObject.service.GameObjectService;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.scene.servcie.GameSceneService;
import com.wan37.gameServer.manager.notification.NotificationManager;
import com.wan37.gameServer.manager.task.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    /**
     *  进入副本，将副本与玩家绑定起来
     */
    public GameInstance enterInstance(Player player, Integer instanceId) {
        // 玩家当前的场景

        GameInstance gameInstance = init(player,instanceId);

        // 设置玩家的当前副本
        player.setCurrentGameInstance(gameInstance);
        player.setScene(gameInstance.getId());

        // 副本关闭通知
        taskManager.schedule(gameInstance.getInstanceTime()-10000,() -> {
            notificationManager.notifyPlayer(player,"副本将于十秒后关闭，请准备传送。");
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
     *  退出副本
     */
    public void exitInstance(Player player) {
        if (player.getCurrentGameInstance() != null) {
            // 返回原来的场景
            player.setScene(player.getCurrentGameInstance()
                    .getPlayerFrom().get(player.getId()));

            // 设置当前副本实例为空
            player.setCurrentGameInstance(null);
            notificationManager.notifyPlayer(player,"副本已关闭，你已被传送");
        } else {
            notificationManager.notifyPlayer(player,"你不在副本中");
        }

    }


    /**
     *  初始化的副本实例
     */
    private GameInstance init(Player player, Integer instanceId) {
        GameScene gameScene = gameSceneService.findSceneById(instanceId);
        GameInstance gameInstance = new GameInstance();
        BeanUtils.copyProperties(gameScene,gameInstance);

        // 加载怪物
        gameObjectService.initSceneObject(gameInstance);

        gameInstance.getPlayers().put(player.getId(), player);
        gameInstance.getPlayerFrom().put(player.getId(),player.getScene());

        log.debug(" gameInstance.getMonsters() {}", gameInstance.getMonsters());
        return gameInstance;
    }



}
