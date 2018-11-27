package com.wan37.gameServer.game.gameInstance;

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
 * time 2018/11/26 17:26
 * @version 1.00
 * Description: 副本管理器
 */


@Service
@Slf4j
public class InstanceManager {

    @Resource
    private GameSceneService gameSceneService;

    @Resource
    private TaskManager taskManager;

    @Resource
    private NotificationManager notificationManager;

    @Resource
    private GameObjectService gameObjectService;

    /**
     *  创建副本，将副本也玩家绑定起来
     */
    public GameInstance createInstance(Player player, Integer instanceId) {
        // 传送副本前玩家所在场景
        int positionBefore = player.getScene();


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
           // 设置当前副本实例为空
            player.setCurrentGameInstance(null);
            player.setScene(positionBefore);
            notificationManager.notifyPlayer(player,"副本已关闭，你已被传送");
            return null;
        });

        return gameInstance;
    }


    /**
     *  初始化的副本实例
     */
    private GameInstance init(Player player, Integer instanceId) {
        GameScene gameScene = gameSceneService.findSceneById(instanceId);
        GameInstance gameInstance = new GameInstance();
        BeanUtils.copyProperties(gameScene,gameInstance);


        gameInstance.getPlayers().put(player.getId(), player);

        log.debug("  GameInstance init {}", gameInstance);
        return gameInstance;
    }


}
