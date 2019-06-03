package com.wan37.gameserver.game.scene.manager;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wan37.gameserver.game.player.model.Player;
import com.wan37.gameserver.game.player.service.PlayerDataService;
import com.wan37.gameserver.game.sceneObject.manager.GameObjectManager;
import com.wan37.gameserver.game.sceneObject.model.Monster;

import com.wan37.gameserver.game.sceneObject.model.SceneObject;
import com.wan37.gameserver.game.scene.model.GameScene;


import com.wan37.gameserver.game.sceneObject.service.MonsterAiService;
import com.wan37.gameserver.model.Creature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/11 18:06
 * @version 1.00
 * Description: 场景心跳定时器
 */

@Component
@Slf4j
public class SceneManager {

    /** 单线程定时执行器 */
    private static ThreadFactory sceneLoopThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("sceneLoop-%d").setUncaughtExceptionHandler((t,e) -> e.printStackTrace()).build();
    private static ScheduledExecutorService sceneLoop =
            Executors.newSingleThreadScheduledExecutor(sceneLoopThreadFactory);


    @Resource
    private GameObjectManager gameObjectManager;

    @Resource
    private MonsterAiService monsterAIService;


    @PostConstruct
    private void tick() {
        sceneLoop.scheduleWithFixedDelay(
                this::refreshScene,
                1000, 60, TimeUnit.MILLISECONDS);
        log.debug("场景定时器启动");
    }


    private void refreshScene() {
        List<GameScene>  gameSceneList= SceneCacheMgr.list();
        for (GameScene gameScene : gameSceneList) {
            // 刷新怪物和NPC
            gameScene.getMonsters().values().forEach(this::refreshDeadCreature);
            gameScene.getNpcs().values().forEach(this::refreshDeadCreature);

            // 设置怪物攻击
            gameScene.getMonsters().values().forEach(monster -> monsterAttack(monster,gameScene));

        }
    }


    /**
     *  刷新死亡的生物
     * @param creature  场景中的非玩家生物
     */
    private void refreshDeadCreature(SceneObject creature) {
        if (creature.getState() == -1 &&
                creature.getDeadTime()+ creature.getRefreshTime() < System.currentTimeMillis()) {
            SceneObject sceneObject = gameObjectManager.get(creature.getId());
            creature.setHp(sceneObject.getHp());
            creature.setState(sceneObject.getState());
        }
    }


    /**
     *  刷新怪物攻击
     */
    private void monsterAttack(Monster monster,GameScene gameScene) {

        if (Objects.nonNull(monster.getTarget())) {
            monsterAIService.startAI(monster,gameScene);
        }
    }



}
