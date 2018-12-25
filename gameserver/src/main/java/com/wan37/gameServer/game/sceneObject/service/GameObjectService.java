package com.wan37.gameServer.game.sceneObject.service;


import com.wan37.gameServer.game.sceneObject.manager.GameObjectCacheMgr;
import com.wan37.gameServer.game.sceneObject.model.Monster;
import com.wan37.gameServer.game.sceneObject.model.NPC;
import com.wan37.gameServer.game.scene.model.GameScene;
import com.wan37.gameServer.game.sceneObject.model.SceneObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/10/8 18:30
 * @version 1.00
 * Description: mmorpg
 */

@Service
public class GameObjectService {
    @Resource
    private GameObjectCacheMgr gameObjectCacheMgr;

    public SceneObject getGameObject(long gameObjectId) {
        return gameObjectCacheMgr.get(gameObjectId);
    }


    public void cacheGameObject(long gameObjectId, SceneObject sceneObject) {
        gameObjectCacheMgr.put(gameObjectId, sceneObject);
    }




    /**
     *  场景对象死亡后处理
     * @param sceneObject 场景对象死亡后处理
     * @return
     */
    public boolean sceneObjectAfterDead(SceneObject sceneObject) {
        if (sceneObject.getHp() <= 0) {
            // 重要，设置死亡时间
            sceneObject.setDeadTime(System.currentTimeMillis());
            sceneObject.setHp(0L);
            sceneObject.setState(-1);
            // 重要，清空对象当前目标
            sceneObject.setTarget(null);
            return true;
        } else{
            return false;
        }
    }






    /**
     *      获得场景配置的场景对象
     */
    public GameScene initSceneObject(GameScene gameScene) {
        String  gameObjectIds = gameScene.getGameObjectIds();
        Arrays.stream(gameObjectIds.split(","))
                .map(Long::valueOf)
                .map( this::getGameObject)
                .forEach( sceneObject -> {
                    if ( sceneObject.getRoleType() == 1) {
                        NPC npc = new NPC();
                        BeanUtils.copyProperties(sceneObject,npc);
                        gameScene.getNpcs().put(sceneObject.getId(), npc);
                    }
                    if (sceneObject.getRoleType() == 2 ) {
                        Monster monster = new Monster();
                        BeanUtils.copyProperties(sceneObject,monster);
                        gameScene.getMonsters().put(sceneObject.getId(), monster);
                    }
                }
        );
        return gameScene;
    }


}