package com.wan37.gameServer.game.scene.manager;


import com.wan37.gameServer.game.gameRole.service.PlayerDataService;
import com.wan37.gameServer.game.sceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.sceneObject.model.Monster;

import com.wan37.gameServer.game.sceneObject.model.NPC;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;


import com.wan37.gameServer.game.sceneObject.service.MonsterAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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

    // 单线程定时执行器
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Resource
    private SceneCacheMgr sceneCacheMgr;


    @Resource
    private GameObjectCacheMgr gameObjectCacheMgr;


    @Resource
    private MonsterAIService monsterAIService;

    @Resource
    private PlayerDataService playerDataService;


    @PostConstruct
    private void tick() {
        executorService.scheduleWithFixedDelay(
                this::refreshScene,
                1000, 20, TimeUnit.MILLISECONDS);
        log.debug("场景定时器启动");
    }


    private void refreshScene() {
        List<GameScene>  gameSceneList= sceneCacheMgr.list();
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
                creature.getDeadTime()+ creature.getRefreshTime() <System.currentTimeMillis()) {
            SceneObject sceneObject = gameObjectCacheMgr.get(creature.getId());
            creature.setHp(sceneObject.getHp());
            creature.setState(sceneObject.getState());
        }
    }


    /**
     *  刷新怪物攻击
     */
    private void monsterAttack(Monster monster,GameScene gameScene) {
        if (null != monster.getTarget()) {
            monsterAIService.startAI(monster.getTarget(),monster,gameScene);

        }
    }



}
