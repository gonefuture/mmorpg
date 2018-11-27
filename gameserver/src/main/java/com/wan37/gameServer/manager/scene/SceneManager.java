package com.wan37.gameServer.manager.scene;


import com.wan37.gameServer.game.gameSceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.gameSceneObject.model.Monster;

import com.wan37.gameServer.game.gameSceneObject.model.NPC;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import com.wan37.gameServer.game.gameRole.model.Buffer;
import com.wan37.gameServer.game.gameRole.model.Player;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.gameRole.manager.PlayerCacheMgr;
import com.wan37.gameServer.game.scene.manager.SceneCacheMgr;


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

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);

    @Resource
    private SceneCacheMgr sceneCacheMgr;

    @Resource
    private PlayerCacheMgr playerCacheMgr;

    @Resource
    private GameObjectCacheMgr gameObjectCacheMgr;


    @PostConstruct
    private void tick() {
        executorService.scheduleAtFixedRate(
                this::refreshScene,
                1000, 20, TimeUnit.MILLISECONDS);
        log.debug("场景定时器启动");
    }


    private void refreshScene() {
        List<GameScene>  gameSceneList= sceneCacheMgr.list();
        for (GameScene gameScene : gameSceneList) {
            // 刷新怪物和NPC
            refreshMonsters(gameScene);
            refreshNPCs(gameScene);
            refreshBuffer(gameScene);
        }
    }


    private void refreshMonsters(GameScene gameScene) {

        Map<Long,Monster> monsterMap = gameScene.getMonsters();
        for (Monster monster : monsterMap.values()) {
            // 检测怪物是否死亡和到达刷新时间
            if (monster.getState() == -1 &&
                    monster.getDeadTime()+monster.getRefreshTime() <System.currentTimeMillis()) {
                SceneObject sceneObject = gameObjectCacheMgr.get(monster.getId());
                monster.setHp(sceneObject.getHp());
                monster.setState(sceneObject.getState());
            }
        }
    }


    private void refreshNPCs(GameScene gameScene) {

        Map<Long, NPC> npcMap = gameScene.getNpcs();
        for (NPC npc : npcMap.values()) {
            if (npc.getState() == -1 &&
                    npc.getDeadTime()+ npc.getRefreshTime() <System.currentTimeMillis()) {
                SceneObject sceneObject = gameObjectCacheMgr.get(npc.getId());
                npc.setHp(sceneObject.getHp());
                npc.setState(npc.getState());
            }
        }
    }


    private void refreshBuffer(GameScene gameScene) {
        Map<Long,Player> playerMap = gameScene.getPlayers();
        for ( Player player : playerMap.values()) {
            List<Buffer> bufferList = player.getBufferList();
            for (Buffer buffer : bufferList) {
                long now  = System.currentTimeMillis();
                // 间隔时间进度
                long progress = buffer.getStartTime() ;
                //log.debug("progress {}", progress);
                //log.debug("now {}", now);
                // 只有当当前时间超过预定的间隔时间时才会触发效果
                if (  progress <= now &&
                        buffer.getTimes() != 0) {    // 静态buffer
                    bufferEffect(player,buffer);
                    // 如果持续时间是永久的，就不用减少生效次数
                    if (buffer.getDuration() != -1)
                        buffer.setTimes(buffer.getTimes() -1 );
                    // 加上间隔时间
                    buffer.setStartTime(progress + buffer.getIntervalTime());
                    //log.debug("buffer.getStartTime(){}", buffer.getStartTime());
                }
            }
        }
    }

    /**
     *  buffer对于玩家的作用效果
     */
    private void bufferEffect(Player player, Buffer buffer) {

        long baseHp = player.getRolePropertyMap().get(1).getValue();
        long baseMp = player.getRolePropertyMap().get(1).getValue();

        long changeHp = player.getHp()+ buffer.getHp();
        long changeMp =player.getMp() + buffer.getMp();


        if ( changeHp > baseHp) {
            player.setHp(baseHp);
        } else if (changeHp < 0) {
            player.setHp((long)0);
        }

        if ( changeMp > baseMp) {
            player.setMp(baseMp);
        } else if (changeMp < 0) {
            player.setMp((long)0);
        }

        //log.debug("player {},hp {} ,Mp() {}",player,player.getHp(),player.getMp());
    }

}
